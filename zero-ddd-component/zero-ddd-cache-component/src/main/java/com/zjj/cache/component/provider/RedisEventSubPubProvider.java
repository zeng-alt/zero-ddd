package com.zjj.cache.component.provider;

import com.zjj.autoconfigure.component.l2cache.EventSubPubProvider;
import com.zjj.cache.component.repository.RedisSubPubRepository;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月15日 21:31
 */
@RequiredArgsConstructor
public class RedisEventSubPubProvider<T> implements EventSubPubProvider<T> {

    private final RedisSubPubRepository redisSubPubRepository;

    @Override
    public String addListener(String topic, Class<T> tClass, Consumer<T> listener) {
        return redisSubPubRepository.addListener(topic, tClass, listener);
    }

    @Override
    public long publish(String topic, T event) {
        return redisSubPubRepository.publish(topic, event);
    }
}
