package com.zjj.cache.service.impl;

import com.github.benmanes.caffeine.cache.LoadingCache;
import com.zjj.cache.service.CaffeineService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author zengJiaJun
 * @crateTime 2024年06月16日 21:35
 * @version 1.0
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CaffeineServiceImpl implements CaffeineService {

    private final LoadingCache<String, Object> caffeine;

    @Override
    public <T> T get(String key, Class<T> clazz) {
        return clazz.cast(caffeine.get(key));
    }

    @Override
    public void put(String key, Object value) {
        caffeine.put(key, value);
    }

    @Override
    public void putIfAbsent(String key, Object value) {
        if (containsKey(key)) return;
        put(key, value);
    }

    @Override
    public boolean containsKey(String key) {
        return caffeine.getIfPresent(key) != null;
    }

    @Override
    public void remove(String key) {
        caffeine.invalidate(key);
    }

    @Override
    public void removeAll() {
        caffeine.invalidateAll();
    }

    @Override
    public void removeByPattern(String pattern) {
        caffeine.asMap().keySet().stream().filter(key -> key.startsWith(pattern)).forEach(caffeine::invalidate);
    }

    @Override
    public void removeByPattern(String... patterns) {
        for (String pattern : patterns) {
            removeByPattern(pattern);
        }
    }
}
