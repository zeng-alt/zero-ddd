package com.zjj.autoconfigure.component.l2cache;

import java.util.function.Consumer;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月15日 21:28
 */
public interface EventSubPubProvider<T> {

    String addListener(String topic, Class<T> tClass, Consumer<T> listener);


    long publish(String topic, T t);
}
