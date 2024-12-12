package com.zjj.cache.component.repository.impl;

import com.zjj.autoconfigure.component.redis.RedisHashRepository;
import org.redisson.api.RedissonClient;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月10日 21:29
 */
public class RedisHashRepositoryImpl extends RedisHashRepository {
    public RedisHashRepositoryImpl(RedissonClient template) {
        super(template);
    }

    @Override
    public <T> T get(String key) {
        return null;
    }

    @Override
    public void put(String key, Object value) {

    }

    @Override
    public void putIfAbsent(String key, Object value) {

    }
}
