package com.zjj.l2.cache.component.supper;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.zjj.autoconfigure.component.l2cache.provider.EventSubPubProvider;
import com.zjj.autoconfigure.component.l2cache.L2Cache;
import com.zjj.autoconfigure.component.l2cache.L2CacheManage;
import com.zjj.autoconfigure.component.redis.RedisStringRepository;
import com.zjj.l2.cache.component.config.properties.L2CacheProperties;
import com.zjj.autoconfigure.component.l2cache.CacheOperation;
import com.zjj.autoconfigure.component.l2cache.EvictEvent;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月11日 20:49
 */
@Slf4j
public class RedissonCaffeineCacheManage implements L2RedissonCaffeineCacheManage {

    private Caffeine<Object, Object> cacheBuilder = Caffeine.newBuilder();
    private final Collection<String> customCacheNames = new CopyOnWriteArrayList<>();
    private final Map<String, Cache> cacheMap = new ConcurrentHashMap<>(16);
    private final L2CacheProperties l2CacheProperties;
    private final RedisStringRepository redisStringRepository;
    private final EventSubPubProvider<EvictEvent> eventSubPubProvider;

    private UnaryOperator<String> cacheNamePrefix = name -> name;

    private final boolean dynamic;
    private Object serverId;

    public RedissonCaffeineCacheManage(L2CacheProperties l2CacheProperties,
                                       RedisStringRepository redisStringRepository,
                                       EventSubPubProvider eventSubPubProvider) {
        super();

        this.eventSubPubProvider = eventSubPubProvider;
        this.l2CacheProperties = l2CacheProperties;
        this.redisStringRepository = redisStringRepository;
        this.dynamic = l2CacheProperties.isDynamic();
        if (StringUtils.hasText(l2CacheProperties.getL1Cache().getSpec())) {
            setCacheSpecification(l2CacheProperties.getL1Cache().getSpec());
        }
        this.serverId = l2CacheProperties.getServerId();
    }

    public void setCacheNames(@Nullable Collection<String> cacheNames) {
        if (cacheNames != null) {
            for (String name : cacheNames) {
                this.cacheMap.put(cacheNamePrefix.apply(name), createCache(cacheNamePrefix.apply(name)));
            }
        }
    }

    @Override
    public void setCacheNamePrefix(UnaryOperator<String> cacheNamePrefix) {
        this.cacheNamePrefix = cacheNamePrefix;
    }

    public void setCacheSpecification(String cacheSpecification) {
        doSetCaffeine(Caffeine.from(cacheSpecification));
    }

    public void registerCustomCache(String name, com.github.benmanes.caffeine.cache.Cache<Object, Object> cache) {
        checkCacheName(name);
        this.customCacheNames.add(name);
        this.cacheMap.put(name, adaptCaffeineCache(name, cache));
    }


    public void registerCustomCache(String name, RedissonCaffeineCache.Builder builder) {
        checkCacheName(name);
        this.customCacheNames.add(name);
        builder
                .cacheNullValue(l2CacheProperties.isCacheNullValues())
                .serverId(serverId)
                .eventSubPubProvider(eventSubPubProvider)
                .redisStringRepository(redisStringRepository)
                .l2(l2 -> {
                    if (!StringUtils.hasText(l2.topic)) {
                        l2.topic(l2CacheProperties.getL2Cache().getTopic());

                    }
                    if (!StringUtils.hasText(l2.getKeyPrefix)) {
                        l2.getKeyPrefix(l2CacheProperties.getCachePrefix());
                    }
                    if (l2.expire == null) {
                        l2.expire(l2CacheProperties.getL2Cache().getDefaultExpiration());
                    }
                    if (l2.nullValueExpiration == null && l2CacheProperties.isCacheNullValues()) {
                        l2.nullValueExpiration(l2CacheProperties.getL2Cache().getDefaultNullValuesExpiration());
                    }

                });
        this.cacheMap.put(name, builder.build());
    }

    private void checkCacheName(String name) {

        if (cacheMap.containsKey(name)) {
            throw new IllegalArgumentException("The name '" + name + "' is already in use");
        }
    }

    @Override
    public Cache getCache(@NonNull String name) {
        Cache cache = cacheMap.get(name);
        if (cache != null) {
            return cache;
        }
        if (!dynamic) {
            return null;
        }

        cache = createCache(name);
        Cache oldCache = cacheMap.putIfAbsent(name, cache);
        log.debug("create cache instance, the cache name is : {}", name);
        return oldCache == null ? cache : oldCache;
    }

    @Override
    @NonNull
    public Collection<String> getCacheNames() {
        return Collections.unmodifiableSet(this.cacheMap.keySet());
    }


    public void clearLocal(String cacheName, Object key) {
        clearLocal(cacheName, key, CacheOperation.EVICT);
    }

    @SuppressWarnings("unchecked")
    public void clearLocal(String cacheName, Object key, CacheOperation operation) {
        Cache cache = cacheMap.get(cacheName);
        if (cache == null) {
            return;
        }

        L2Cache l2Cache = (L2Cache) cache;
        if (CacheOperation.EVICT_BATCH.equals(operation)) {
            l2Cache.clearLocalBatch((Iterable<Object>) key);
        }
        else {
            l2Cache.clearLocal(key);
        }
    }

    protected Cache adaptCaffeineCache(String name, com.github.benmanes.caffeine.cache.Cache<Object, Object> cache) {
        return new RedissonCaffeineCache(name, cache, redisStringRepository, l2CacheProperties, l2CacheProperties.isCacheNullValues(), eventSubPubProvider);
    }

    protected Cache createCache(String name) {
        return adaptCaffeineCache(name, createNativeCaffeineCache());
    }

    protected com.github.benmanes.caffeine.cache.Cache<Object, Object> createNativeCaffeineCache() {
        return this.cacheBuilder.build();
    }


    private void doSetCaffeine(Caffeine<Object, Object> cacheBuilder) {
        if (!ObjectUtils.nullSafeEquals(this.cacheBuilder, cacheBuilder)) {
            this.cacheBuilder = cacheBuilder;
            refreshCommonCaches();
        }
    }

    private void refreshCommonCaches() {
        for (Map.Entry<String, Cache> entry : this.cacheMap.entrySet()) {
            if (!this.customCacheNames.contains(entry.getKey())) {
                entry.setValue(createCache(entry.getKey()));
            }
        }
    }

    @Override
    public <K, V> L2Cache<K, V> getL2Cache(String cacheName) {
        Cache cache = getCache(cacheNamePrefix.apply(cacheName));
        return (L2Cache<K, V>) cache;
    }

    @Override
    public Object getServerId() {
        return serverId;
    }
}
