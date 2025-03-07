package com.zjj.excel.component.listener;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.converters.Converter;
import cn.idev.excel.converters.ConverterKeyBuild;
import cn.idev.excel.converters.ReadConverterContext;
import cn.idev.excel.enums.HeadKindEnum;
import cn.idev.excel.enums.ReadDefaultReturnEnum;
import cn.idev.excel.metadata.data.DataFormatData;
import cn.idev.excel.metadata.data.ReadCellData;
import cn.idev.excel.metadata.property.ExcelContentProperty;
import cn.idev.excel.read.listener.IgnoreExceptionReadListener;
import cn.idev.excel.read.metadata.holder.ReadSheetHolder;
import cn.idev.excel.support.cglib.beans.BeanMap;
import cn.idev.excel.util.ClassUtils;
import cn.idev.excel.util.ConverterUtils;
import cn.idev.excel.util.DateUtils;
import cn.idev.excel.util.MapUtils;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月05日 16:31
 */
public abstract class AbstractReadListener<T> implements I18nReadListener<T, Map<Integer, ReadCellData<?>>>, ValidaReadListener<T>, IgnoreExceptionReadListener<Map<Integer, ReadCellData<?>>> {

    private final Class<T> clazz;
    private final  Map<Integer, List<Head>> headMap = new ConcurrentHashMap<>();

    public AbstractReadListener(Class<T> clazz) {
        this.clazz = clazz;

    }
    @Override
    public void invokeHeadMap(Map<Integer, List<Head>> headMap, AnalysisContext context) {
        this.headMap.putAll(headMap);
    }

    @Override
    public Class<T> getEntityClass() {
        return clazz;
    }

    @Override
    public void invoke(Map<Integer, ReadCellData<?>> cellData, AnalysisContext analysisContext) {
        ReadSheetHolder readSheetHolder = analysisContext.readSheetHolder();
        if (HeadKindEnum.CLASS.equals(readSheetHolder.excelReadHeadProperty().getHeadKind())) {
            analysisContext.readRowHolder()
                    .setCurrentRowAnalysisResult(buildUserModel(cellData, analysisContext));
            return;
        }
        analysisContext.readRowHolder().setCurrentRowAnalysisResult(buildNoModel(cellData, readSheetHolder, analysisContext));
    }

    private void buildHead(AnalysisContext context, Head head, T object, ReadCellData<?> readCellData) {
        BeanMap beanMap = BeanMap.create(object);
        ExcelContentProperty excelContentProperty = ClassUtils.declaredExcelContentProperty(beanMap, head.getPropertyType(),
                head.getFieldName(), context.readSheetHolder());
        Converter<?> converter = null;
        if (excelContentProperty != null) {
            converter = excelContentProperty.getConverter();
        }

        if (converter == null) {
            Map<ConverterKeyBuild.ConverterKey, Converter<?>> converterMap = context.readSheetHolder().converterMap();
            converter = converterMap.get(ConverterKeyBuild.buildKey(head.getPropertyType(), readCellData.getType()));
        }

        head.setExcelContentProperty(excelContentProperty);
        head.setConverter(converter);
    }

    private Object buildNoModel(Map<Integer, ReadCellData<?>> cellDataMap, ReadSheetHolder readSheetHolder,
                                AnalysisContext context) {
        int index = 0;
        Map<Integer, Object> map = MapUtils.newLinkedHashMapWithExpectedSize(cellDataMap.size());
        for (Map.Entry<Integer, ReadCellData<?>> entry : cellDataMap.entrySet()) {
            Integer key = entry.getKey();
            ReadCellData<?> cellData = entry.getValue();
            while (index < key) {
                map.put(index, null);
                index++;
            }
            index++;

            ReadDefaultReturnEnum readDefaultReturn = context.readWorkbookHolder().getReadDefaultReturn();
            if (readDefaultReturn == ReadDefaultReturnEnum.STRING) {
                // string
                map.put(key,
                        (String)ConverterUtils.convertToJavaObject(cellData, null, null, readSheetHolder.converterMap(),
                                context, context.readRowHolder().getRowIndex(), key));
            } else {
                // return ReadCellData
                ReadCellData<?> convertedReadCellData = convertReadCellData(cellData,
                        context.readWorkbookHolder().getReadDefaultReturn(), readSheetHolder, context, key);
                if (readDefaultReturn == ReadDefaultReturnEnum.READ_CELL_DATA) {
                    map.put(key, convertedReadCellData);
                } else {
                    map.put(key, convertedReadCellData.getData());
                }
            }
        }
        // fix https://github.com/CodePhiliaX/fastexcel/issues/2014
        int headSize = calculateHeadSize(readSheetHolder);
        while (index < headSize) {
            map.put(index, null);
            index++;
        }
        return map;
    }

    private ReadCellData convertReadCellData(ReadCellData<?> cellData, ReadDefaultReturnEnum readDefaultReturn,
                                             ReadSheetHolder readSheetHolder, AnalysisContext context, Integer columnIndex) {
        Class<?> classGeneric;
        switch (cellData.getType()) {
            case STRING:
            case DIRECT_STRING:
            case ERROR:
            case EMPTY:
                classGeneric = String.class;
                break;
            case BOOLEAN:
                classGeneric = Boolean.class;
                break;
            case NUMBER:
                DataFormatData dataFormatData = cellData.getDataFormatData();
                if (dataFormatData != null && DateUtils.isADateFormat(dataFormatData.getIndex(),
                        dataFormatData.getFormat())) {
                    classGeneric = LocalDateTime.class;
                } else {
                    classGeneric = BigDecimal.class;
                }
                break;
            default:
                classGeneric = ConverterUtils.defaultClassGeneric;
                break;
        }

        return (ReadCellData)ConverterUtils.convertToJavaObject(cellData, null, ReadCellData.class,
                classGeneric, null, readSheetHolder.converterMap(), context, context.readRowHolder().getRowIndex(),
                columnIndex);
    }

    private int calculateHeadSize(ReadSheetHolder readSheetHolder) {
        if (readSheetHolder.excelReadHeadProperty().getHeadMap().size() > 0) {
            return readSheetHolder.excelReadHeadProperty().getHeadMap().size();
        }
        if (readSheetHolder.getMaxNotEmptyDataHeadSize() != null) {
            return readSheetHolder.getMaxNotEmptyDataHeadSize();
        }
        return 0;
    }

    private T buildUserModel(Map<Integer, ReadCellData<?>> cellData, AnalysisContext analysisContext) {
        T object = BeanUtils.instantiateClass(clazz);
        for (Map.Entry<Integer, ReadCellData<?>> entry : cellData.entrySet()) {
            List<Head> heads = headMap.get(entry.getKey());
            for (Head head : heads) {
                if (head.getConverter() == null) {
                    synchronized (heads) {
                        if (head.getConverter() == null) {
                            buildHead(analysisContext, head, object, entry.getValue());
                        }
                    }
                }
                try {
                    head
                            .getWriteMethod()
                            .invoke(
                                    object,
                                    head.getConverter()
                                            .convertToJavaData(new ReadConverterContext<>(entry.getValue(), head.getExcelContentProperty(), analysisContext))
                            );
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        this.valida(object, analysisContext);
        this.invokeObject(object, analysisContext);
        return object;
    }

    public void invokeObject(T t, AnalysisContext context) {}


}
