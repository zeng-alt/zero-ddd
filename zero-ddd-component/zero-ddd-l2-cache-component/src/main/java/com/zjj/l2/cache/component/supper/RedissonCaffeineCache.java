package com.zjj.l2.cache.component.supper;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Policy;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import com.zjj.cache.component.repository.RedisStringRepository;
import com.zjj.l2.cache.component.config.properties.L2CacheProperties;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.nullness.qual.PolyNull;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月11日 20:11
 */
@Slf4j
public class RedissonCaffeineCache extends AbstractValueAdaptingCache implements L2Cache<Object, Object> {

    private final String name;
    private final Cache<Object, Object> caffeineCache;
    private final RedisStringRepository redisStringRepository;
    private final L2CacheProperties l2CacheProperties;
    private final String cachePrefix;
    private final String getKeyPrefix;
    private final Object serverId;
    private final Duration defaultExpiration;
    private final Duration defaultNullValuesExpiration;
    private final Map<String, Duration> expires;
    private final String topic;

    public RedissonCaffeineCache(
            String name, Cache<Object, Object> cache,
            RedisStringRepository redisStringRepository, L2CacheProperties l2CacheProperties,
            boolean cacheNullValues
    ) {
        super(cacheNullValues);
        this.name = name;
        this.caffeineCache = cache;
        this.redisStringRepository = redisStringRepository;
        this.l2CacheProperties = l2CacheProperties;
        this.cachePrefix = l2CacheProperties.getCachePrefix();
        if (StringUtils.hasLength(cachePrefix)) {
            this.getKeyPrefix = name + ":" + cachePrefix + ":";
        }
        else {
            this.getKeyPrefix = name + ":";
        }
        this.defaultExpiration = l2CacheProperties.getL2Cache().getDefaultExpiration();
        this.defaultNullValuesExpiration = l2CacheProperties.getL2Cache().getDefaultNullValuesExpiration();
        this.expires = l2CacheProperties.getL2Cache().getExpires();
        this.topic = l2CacheProperties.getL2Cache().getTopic();
        this.serverId = l2CacheProperties.getServerId();
    }

    @Override
    public @Nullable Object getIfPresent(Object key) {
        return null;
    }

    @Override
    public @PolyNull Object get(Object key, Function<? super Object, ?> mappingFunction) {
        return null;
    }

    @Override
    public Map<Object, Object> getAllPresent(Iterable<? extends Object> keys) {
        return null;
    }

    @Override
    public Map<Object, Object> getAll(Iterable<?> keys, Function<? super Set<?>, ? extends Map<?, ?>> mappingFunction) {
        return null;
    }


    @Override
    public void put(Object key, Object value) {

    }


    @Override
    public void invalidate(Object key) {

    }

    @Override
    public void invalidateAll(Iterable<? extends Object> keys) {

    }


    @Nullable
    @Override
    protected Object lookup(Object key) {
        if (key == null) {
            throw new IllegalArgumentException("key must not be null");
        }
        String cacheName = key.toString();
        String cacheKey = getKey(cacheName);
        Object value = getL1Value(cacheName);
        if (value != null) {
            log.debug("get cache from caffeine, the key is : {}", cacheKey);
            return value;
        }

        value = getL2Value(key.toString());

        if (value != null) {
            log.debug("get cache from redis and put in caffeine, the key is : {}", cacheKey);
            setL1Value(cacheName, value);
        }
        return value;
    }

    public void setL1Value(Object key, Object value) {
    }

    @Override
    public Object getL2Value(String key) {
        return redisStringRepository.get(key);
    }

    @Override
    public Object getL1Value(Object key) {
        return caffeineCache.getIfPresent(key);
    }


    protected String getKey(Object key) {
        return this.getKeyPrefix + key;
    }


    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        return null;
    }


    @Override
    public void putAll(Map<? extends Object, ?> map) {

    }

    @Override
    public void evict(Object key) {

    }

    @Override
    public void clear() {
    }


    @Override
    public void invalidateAll() {
        this.clear();
    }

    @Override
    public @NonNegative long estimatedSize() {
        return caffeineCache.estimatedSize();
    }

    @Override
    public CacheStats stats() {
        return caffeineCache.stats();
    }

    @Override
    public ConcurrentMap<Object, Object> asMap() {
        return caffeineCache.asMap();
    }

    @Override
    public Object getNativeCache() {
        return this;
    }

    @Override
    public void cleanUp() {
        caffeineCache.cleanUp();
    }

    @Override
    public Policy<Object, Object> policy() {
        return caffeineCache.policy();
    }

    @Override
    public String getName() {
        return name;
    }
}
