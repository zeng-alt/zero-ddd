package com.zjj.autoconfigure.component.redis;

import org.redisson.api.RedissonClient;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年06月18日 18:56
 */
public abstract class RedisZSetRepository<V> extends RedisCrudRepository<String, V> {

    public RedisZSetRepository(RedissonClient template) {
        super(template);
    }
}
