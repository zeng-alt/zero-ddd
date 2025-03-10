package com.zjj.excel.component.listener;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.event.AnalysisEventListener;
import cn.idev.excel.metadata.FieldCache;
import cn.idev.excel.metadata.FieldWrapper;
import cn.idev.excel.metadata.data.ReadCellData;
import com.zjj.core.component.utils.ClassUtils;
import com.zjj.excel.component.dynamic.AbaDynamicColumn;
import com.zjj.excel.component.dynamic.DynamicEntity;
import com.zjj.excel.component.dynamic.InterfaceDynamicColumn;
import com.zjj.excel.component.exception.DynamicReadExcelException;
import com.zjj.excel.component.utils.ValidaHelper;
import com.zjj.i18n.component.MessageSourceHelper;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Option;
import lombok.Getter;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月01日 21:33
 */
@Getter
public abstract class DynamicReadListener<T extends InterfaceDynamicColumn<E>, E extends DynamicEntity> extends AnalysisEventListener<Map<Integer, ReadCellData<?>>> {

    protected Map<Integer, Tuple2<String, String>> headMap = new LinkedHashMap<>();
    protected final Class<T> clazz;
    protected Class<E> dynamicClazz;


    public DynamicReadListener(Class<T> clazz) {
        Assert.notNull(clazz, "请指定泛型");
        this.clazz = clazz;
        Type type = ClassUtils.findGenericType(clazz, AbaDynamicColumn.class, InterfaceDynamicColumn.class);
        if (type instanceof ParameterizedType parameterizedType) {
            this.dynamicClazz = (Class<E>) parameterizedType.getActualTypeArguments()[0];
        }
        Assert.notNull(dynamicClazz, "请指定动态列泛型");
    }

    public DynamicReadListener(Class<T> clazz, Class<E> dynamicClazz) {
        Assert.notNull(clazz, "请指定泛型");
        Assert.notNull(dynamicClazz, "请指定动态列泛型");
        this.clazz = clazz;
        this.dynamicClazz = dynamicClazz;
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        for (Map.Entry<Integer, String> entry : headMap.entrySet()) {
            String value = entry.getValue();
            String template = value;
            if (value.startsWith("{") && value.endsWith("}")) {
                template = MessageSourceHelper.getMessage(value, value);
            }
            this.headMap.put(entry.getKey(), Tuple.of(value, template));
        }
    }

    @Override
    public void invoke(Map<Integer, ReadCellData<?>> cellData, AnalysisContext analysisContext) {
        T object = instantiateObject();

        FieldCache fieldCache = cn.idev.excel.util.ClassUtils.declaredFields(clazz, analysisContext.currentReadHolder());
        Map<Integer, String> dataMap = cellData.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> String.valueOf(e.getValue()), (v1, v2) -> v1));
        Set<Integer> dataIndex = dataMap.keySet();

        Map<Integer, FieldWrapper> sortedFieldMap = fieldCache.getSortedFieldMap();

        for (Map.Entry<Integer, FieldWrapper> entry : sortedFieldMap.entrySet()) {
            FieldWrapper value = entry.getValue();
            String[] heads = value.getHeads();
            if (heads == null || heads.length == 0) {
                continue;
            }

            String data = dataMap.get(entry.getKey());


            Option
                    .of(BeanUtils.getPropertyDescriptor(object.getClass(), value.getFieldName()))
                    .map(PropertyDescriptor::getWriteMethod)
                    .forEach(m -> {
                        try {
                            m.invoke(object, data);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new DynamicReadExcelException("访问 [" + value.getFieldName() + "] 的set方法权限不够，或者没有找到set方法！！！");
                        }
                    });

            dataIndex.remove(entry.getKey());

        }


        for (Integer index : dataIndex) {
            E dynamicEntity = BeanUtils.instantiateClass(dynamicClazz);
            String data = dataMap.get(index);
            dynamicEntity.setIndex(index);
            Tuple2<String, String> head = headMap.get(index);
            if (head != null) {
                dynamicEntity.setName(head._1);
                dynamicEntity.setNameTemplate(head._2);
            }
            dynamicEntity.setValue(data);
            object.add(dynamicEntity);
        }

        ValidaHelper.validate(object, analysisContext);
        this.invokeObject(object, analysisContext);
    }

    public T instantiateObject() {
        return BeanUtils.instantiateClass(clazz);
    }

    public abstract void invokeObject(T t, AnalysisContext context);
}
