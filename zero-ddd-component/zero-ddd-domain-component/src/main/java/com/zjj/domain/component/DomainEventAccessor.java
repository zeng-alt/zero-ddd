package com.zjj.domain.component;

import java.util.function.Consumer;

/**
 * 外部事件注册接口
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月15日 21:28
 */
public interface DomainEventAccessor {

    <T> String addListener(String topic, Class<T> tClass, DomainEventConsumer<T> listener);


    <T> long publish(String topic, T t);
}
