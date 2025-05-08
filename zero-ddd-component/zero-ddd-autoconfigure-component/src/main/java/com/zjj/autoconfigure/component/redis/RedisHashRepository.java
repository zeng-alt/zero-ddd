package com.zjj.autoconfigure.component.redis;

import org.redisson.api.RedissonClient;

import java.util.Map;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年06月18日 18:56
 */
public abstract class RedisHashRepository extends RedisCrudRepository<String, Object> {

    public RedisHashRepository(RedissonClient template) {
        super(template);
    }

    public abstract  <T> Map<String, T> getMap(String key);

    public abstract <T> Map<String, T> getAll(String pre, Set<String> keys);

    public abstract void put(String pre, String key, Object value);

    public abstract void batchPut(String preKey, Map<String, Object> map);

    public abstract <T> T get(String pre, String key);
}
