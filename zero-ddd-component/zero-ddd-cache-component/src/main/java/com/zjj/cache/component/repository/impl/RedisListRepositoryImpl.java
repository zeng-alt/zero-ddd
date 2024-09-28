package com.zjj.cache.component.repository.impl;

import com.zjj.cache.component.repository.RedisListRepository;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年06月18日 18:56
 */
public class RedisListRepositoryImpl<V> extends RedisListRepository<V> {

    @Override
    public void addToList(String key, V value) {
        put(key, value);
    }

    @Override
    public V get(String key) {
//        return template.opsForList().index(key, 0);
        return null;
    }

    @Override
    public void put(String key, V value) {
//        template.opsForList().rightPush(key, value);
    }

    @Override
    public void putIfAbsent(String key, V value) {
//        template.opsForList().rightPushIfPresent(key, value);
    }
}
