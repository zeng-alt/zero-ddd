package com.zjj.excel.component.utils;

import com.zjj.excel.component.builder.ExcelTemplate;
import com.zjj.excel.component.rxjava.RxjavaListenerUtils;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.io.InputStream;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月10日 21:05
 */
@Slf4j
public class RxjavaExcelHelper implements BeanFactoryPostProcessor {

    private static ExcelTemplate excelTemplate;


    public static <T> Flowable<T> importRxjavaExcel(InputStream inputStream, Class<T> paramType) {
        return Flowable.create(emitter -> {
            try {
                excelTemplate.read(inputStream, paramType, paramType, RxjavaListenerUtils.createFlowableListener(paramType).apply(emitter))
                        .sheet()
                        .doRead();
            } catch (Exception e) {
                emitter.onError(e);
            }
        }, BackpressureStrategy.BUFFER);
    }

    public static <T> Flowable<T> importRxjavaDynamicExcel(InputStream inputStream, Class<T> paramType) {
        return Flowable.create(emitter -> {
            try {
                excelTemplate.dynamicRead(inputStream, paramType)
                        .registerReadListener(RxjavaListenerUtils.createFlowableListener(paramType).apply(emitter))
                        .sheet()
                        .doRead();
            } catch (Exception e) {
                emitter.onError(e);
            }
        }, BackpressureStrategy.BUFFER);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        excelTemplate = beanFactory.getBean(ExcelTemplate.class);
    }
}
