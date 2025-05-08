package com.zjj.cache.component.repository.impl;

import com.zjj.autoconfigure.component.redis.RedisHashRepository;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

import java.util.Map;
import java.util.Set;

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
        String[] split = key.split(":");
        if (split.length < 2)  {
            return null;
        }
        return this.get(split[0], split[1]);
    }

    public <T> T get(String pre, String key) {
        RMap<String, T> map = this.template.getMap(pre);
        return map.get(key);
    }

    public <T> Map<String, T> getAll(String pre, Set<String> keys) {
        RMap<String, T> map = this.template.getMap(pre);
        return map.getAll(keys);
    }

    public <T> Map<String, T> getMap(String key) {
        RMap<String, T> rMap = this.template.getMap(key);
        return rMap.readAllMap();
    }

    @Override
    public void put(String key, Object value) {
        String[] split = key.split(":");
        if (split.length < 2)  {
            return;
        }
        this.put(split[0], split[1], value);
    }

    public void put(String pre, String key, Object value) {
        RMap<Object, Object> rmap = this.template.getMap(pre);
        rmap.put(key, value);
    }

    public void batchPut(String preKey, Map<String, Object> map) {
        RMap<Object, Object> rmap = this.template.getMap(preKey);
        rmap.putAll(map);
    }


    @Override
    public void putIfAbsent(String key, Object value) {
        String[] split = key.split(":");
        if (split.length < 2)  {
            return;
        }
        this.putIfAbsent(split[0], split[1]);
    }

    public void putIfAbsent(String pre, String key, Object value) {
        if (this.get(pre, key) != null) {
            this.put(pre, key, value);
        }
    }
}
