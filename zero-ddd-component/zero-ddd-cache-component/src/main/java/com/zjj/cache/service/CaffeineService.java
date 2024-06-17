package com.zjj.cache.service;

/**
 * @author zengJiaJun
 * @crateTime 2024年06月16日 21:25
 * @version 1.0
 */
public interface CaffeineService {

    <T> T get(String key, Class<T> clazz);

    void put(String key, Object value);

    void putIfAbsent(String key, Object value);

    boolean containsKey(String key);

    void remove(String key);

    void removeAll();

    void removeByPattern(String pattern);

    void removeByPattern(String... patterns);
}
