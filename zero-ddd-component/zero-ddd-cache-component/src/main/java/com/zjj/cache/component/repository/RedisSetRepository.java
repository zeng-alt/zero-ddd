package com.zjj.cache.component.repository;

import org.redisson.api.RedissonClient;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年06月18日 18:56
 */
public abstract class RedisSetRepository<V> extends RedisCrudRepository<String, V> {

    public RedisSetRepository(RedissonClient template) {
        super(template);
    }
}
