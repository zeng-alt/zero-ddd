package com.zjj.excel.component.listener;

import cn.idev.excel.context.AnalysisContext;
import lombok.Setter;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月05日 16:33
 */
@Setter
public class DefaultReadListener<T> extends AbstractReadListener<T> {

    private BiConsumer<T, AnalysisContext> invokeConsumer;
    private Consumer<AnalysisContext> doAfterConsumer;
    private BiConsumer<Exception, AnalysisContext> onExceptionConsumer;

    public DefaultReadListener(Class<T> clazz) {
        super(clazz);
    }

    @Override
    public void invokeObject(T t, AnalysisContext context) {
        if (invokeConsumer != null) {
            invokeConsumer.accept(t, context);
        }
    }

    /**
     * if have something to do after all analysis
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (doAfterConsumer != null) {
            doAfterConsumer.accept(context);
        }
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        if (onExceptionConsumer != null) {
            onExceptionConsumer.accept(exception, context);
        } else {
            super.onException(exception, context);
        }
    }
}
