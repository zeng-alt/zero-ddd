package com.zjj.excel.component.listener;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.read.listener.ReadListener;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年02月25日 18:47
 */
public interface ReadValidaListener<T> extends ReadListener<T> {

    default void invoke(T data, AnalysisContext context) {
//        ValidaHelper.validate(data, context);
        validation(data, context);
    }

    void validation(T data, AnalysisContext context);
}
