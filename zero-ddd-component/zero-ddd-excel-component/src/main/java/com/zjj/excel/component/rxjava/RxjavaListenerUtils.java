package com.zjj.excel.component.rxjava;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.event.AnalysisEventListener;
import cn.idev.excel.read.listener.ReadListener;
import io.reactivex.rxjava3.core.FlowableEmitter;

import java.util.function.Function;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月17日 21:10
 */
public class RxjavaListenerUtils {
    private RxjavaListenerUtils() {}

    public static <T> Function<FlowableEmitter<T>, ReadListener<T>> createFlowableListener(Class<T> paramType) {
        return emitter -> new AnalysisEventListener<T>() {

            @Override
            public void onException(Exception exception, AnalysisContext context) throws Exception {
                emitter.onError(exception);
            }


            @Override
            public void invoke(T data, AnalysisContext context) {
                emitter.onNext(data);
            }


            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                emitter.onComplete();
            }
        };
    }
}
