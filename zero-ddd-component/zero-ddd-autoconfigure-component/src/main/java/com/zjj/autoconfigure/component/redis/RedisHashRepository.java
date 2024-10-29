package com.zjj.autoconfigure.component.redis;

import org.redisson.api.RedissonClient;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年06月18日 18:56
 */
public abstract class RedisHashRepository<V> extends RedisCrudRepository<String, V> {

    public RedisHashRepository(RedissonClient template) {
        super(template);
    }
}
