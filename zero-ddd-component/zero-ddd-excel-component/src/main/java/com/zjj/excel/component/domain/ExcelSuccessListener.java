package com.zjj.excel.component.domain;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.ReadListener;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年07月05日 20:38
 */
@Slf4j
public abstract class ExcelSuccessListener<T> extends AnalysisEventListener<T> implements ReadListener<T>, ExcelResult<T>, ImportCall<T> {

    protected List<T> list;
    protected List<String> errorList;
    protected Map<Integer, String> headMap;

    protected ExcelSuccessListener() {
        this.list = Lists.newArrayList();
        this.errorList = Lists.newArrayList();
    }

    @Override
    public void invokeHeadMap(java.util.Map<Integer, String> headMap, AnalysisContext context) {
        this.headMap = HashMap.ofAll(headMap);
        log.debug("解析到表头数据: {}", headMap);
    }

    @Override
    public String call(Consumer<List<T>> success, Consumer<List<String>> error) {
        if (list.isEmpty()) {
            error.accept(errorList);
            return "解析失败";
        }

        if (errorList.isEmpty()) {
            success.accept(list);
            return "解析成功";
        }

        error.accept(errorList);
        return "解析失败";
    }

    @Override
    public List<String> getErrorList() {
        return errorList;
    }

    @Override
    public List<T> getResult() {
        return list;
    }
}
