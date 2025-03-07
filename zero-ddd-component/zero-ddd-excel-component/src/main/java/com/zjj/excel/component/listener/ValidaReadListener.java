package com.zjj.excel.component.listener;


import cn.idev.excel.context.AnalysisContext;
import com.zjj.excel.component.utils.ValidaHelper;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月04日 09:20
 */
public interface ValidaReadListener<T> {

    default void valida(T t, AnalysisContext analysisContext) {
        ValidaHelper.validate(t, analysisContext);
    }
}
