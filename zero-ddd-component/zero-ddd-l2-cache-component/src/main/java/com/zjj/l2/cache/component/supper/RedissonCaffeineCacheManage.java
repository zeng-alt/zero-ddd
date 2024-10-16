package com.zjj.l2.cache.component.supper;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.zjj.autoconfigure.component.l2cache.EventSubPubProvider;
import com.zjj.autoconfigure.component.l2cache.L2Cache;
import com.zjj.autoconfigure.component.l2cache.L2CacheManage;
import com.zjj.cache.component.repository.RedisStringRepository;
import com.zjj.l2.cache.component.config.properties.L2CacheProperties;
import com.zjj.autoconfigure.component.l2cache.CacheOperation;
import com.zjj.autoconfigure.component.l2cache.EvictEvent;
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

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月11日 20:49
 */
@Slf4j
public class RedissonCaffeineCacheManage implements L2CacheManage {

    private Caffeine<Object, Object> cacheBuilder = Caffeine.newBuilder();
    private final Collection<String> customCacheNames = new CopyOnWriteArrayList<>();
    private final Map<String, Cache> cacheMap = new ConcurrentHashMap<>(16);
    private final L2CacheProperties l2CacheProperties;
    private final RedisStringRepository redisStringRepository;
    private final EventSubPubProvider<EvictEvent> eventSubPubProvider;

//    @Nullable
//    private AsyncCacheLoader<Object, Object> cacheLoader;

    private boolean dynamic;
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
        setCacheNames(l2CacheProperties.getCacheNames());
        this.serverId = l2CacheProperties.getServerId();
    }

    public void setCacheNames(@Nullable Collection<String> cacheNames) {
        if (cacheNames != null) {
            for (String name : cacheNames) {
                this.cacheMap.put(name, createCache(name));
            }
        }
    }

    public void setCacheSpecification(String cacheSpecification) {
        doSetCaffeine(Caffeine.from(cacheSpecification));
    }

    public void registerCustomCache(String name, com.github.benmanes.caffeine.cache.Cache<Object, Object> cache) {
        this.customCacheNames.add(name);
        this.cacheMap.put(name, adaptCaffeineCache(name, cache));
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
        Cache cache = getCache(cacheName);
        return (L2Cache<K, V>) cache;
    }

    @Override
    public Object getServerId() {
        return serverId;
    }
}
