package com.zjj.core.component.api;

import com.zjj.bean.componenet.BeanHelper;
import io.github.linpeilie.Converter;
import jakarta.annotation.Resource;

import java.util.function.Consumer;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月19日 21:12
 */
public abstract class BaseController {

    @FunctionalInterface
    public interface Convert {
        <T> Consumer<Consumer<T>> to(Class<T> t);
    }


    @Resource
    protected Converter converter;

    public <S, T> T convert(S s, Class<T> t) {
        return converter.convert(s, t);
    }

    public <S> Convert beanConvert(S s) {
        return new Convert() {
            @Override
            public <T> Consumer<Consumer<T>> to(Class<T> t) {
                return tConsumer -> tConsumer.accept(BeanHelper.copyToObject(s, t));
            }
        };
    }

    public <S> Convert convert(S s) {
        return new Convert() {
            @Override
            public <T> Consumer<Consumer<T>> to(Class<T> t) {
                return tConsumer -> tConsumer.accept(convert(s, t));
            }
        };
    }

    public <S, T> Consumer<Consumer<T>> convertRun(S s, Class<T> t) {
        return tConsumer -> tConsumer.accept(convert(s, t));
    }
}
