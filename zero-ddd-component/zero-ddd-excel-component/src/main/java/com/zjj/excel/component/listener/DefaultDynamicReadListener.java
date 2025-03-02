package com.zjj.excel.component.listener;

import cn.idev.excel.context.AnalysisContext;
import com.zjj.excel.component.dynamic.DynamicEntity;
import com.zjj.excel.component.dynamic.InterfaceDynamicColumn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年02月28日 16:09
 */
public class DefaultDynamicReadListener<T extends InterfaceDynamicColumn<E>, E extends DynamicEntity> extends DynamicReadListener<T, E> {

    public final List<T> list = new ArrayList<>();

    public DefaultDynamicReadListener(Class<T> clazz) {
        super(clazz);
    }

    @Override
    public void invokeObject(T t, AnalysisContext analysisContext) {
        list.add(t);
    }


    public static <T extends InterfaceDynamicColumn<E>, E extends DynamicEntity> DynamicReadListener<T, E> getInstance(Class<T> clazz) {
        return new DefaultDynamicReadListener<>(clazz);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    public List<T> getList() {
        return Collections.unmodifiableList(list);
    }
}
