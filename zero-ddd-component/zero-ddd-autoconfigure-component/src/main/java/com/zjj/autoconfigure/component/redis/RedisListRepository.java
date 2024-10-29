package com.zjj.autoconfigure.component.redis;

import org.redisson.api.RedissonClient;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年06月18日 18:56
 */
public abstract class RedisListRepository<V> extends RedisCrudRepository<String, V> {

    public RedisListRepository(RedissonClient template) {
        super(template);
    }

    public abstract void addToList(String key, V value);

}
