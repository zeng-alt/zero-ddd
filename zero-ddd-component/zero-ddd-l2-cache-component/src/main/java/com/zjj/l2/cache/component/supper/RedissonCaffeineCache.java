package com.zjj.l2.cache.component.supper;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Policy;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import com.google.common.collect.Lists;
import com.zjj.autoconfigure.component.l2cache.EventSubPubProvider;
import com.zjj.autoconfigure.component.l2cache.L2Cache;
import com.zjj.cache.component.repository.RedisStringRepository;
import com.zjj.l2.cache.component.config.properties.L2CacheProperties;
import com.zjj.autoconfigure.component.l2cache.CacheOperation;
import com.zjj.autoconfigure.component.l2cache.EvictEvent;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.nullness.qual.PolyNull;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.cache.support.NullValue;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月11日 20:11
 */
@Slf4j
public class RedissonCaffeineCache extends AbstractValueAdaptingCache implements Cache<Object, Object>, L2Cache<Object, Object> {

    private final String name;
    private final Cache<Object, Object> caffeineCache;
    private final RedisStringRepository redisStringRepository;
    private final String getKeyPrefix;
    private final Object serverId;
    private final Duration defaultExpiration;
    private final Duration defaultNullValuesExpiration;
    private final Map<String, Duration> expires;
    private final String topic;
    private final EventSubPubProvider<EvictEvent> eventSubPubProvider;

    public RedissonCaffeineCache(
            String name, Cache<Object, Object> cache,
            RedisStringRepository redisStringRepository, L2CacheProperties l2CacheProperties,
            boolean cacheNullValues,
            EventSubPubProvider<EvictEvent> eventSubPubProvider
    ) {
        super(cacheNullValues);
        this.eventSubPubProvider = eventSubPubProvider;
        this.name = name;
        this.caffeineCache = cache;
        this.redisStringRepository = redisStringRepository;
        String cachePrefix = l2CacheProperties.getCachePrefix();
        if (StringUtils.hasLength(cachePrefix)) {
            this.getKeyPrefix = cachePrefix + ":" + name + ":";
        } else {
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
        ValueWrapper valueWrapper = get(key);
        if (valueWrapper == null) {
            return null;
        }
        return valueWrapper.get();
    }

    @Override
    public @PolyNull Object get(Object key, Function<? super Object, ?> mappingFunction) {
        return get(key, () -> mappingFunction.apply(key));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<Object, Object> getAllPresent(Iterable<? extends Object> keys) {
        CacheGetContext context = CacheGetContext.notSaveRedisAbsentKeys((Iterable<Object>) keys);
        doGetAll(context);
        Map<Object, Object> cachedKeyValues = context.cachedKeyValues;
        Map<Object, Object> result = new HashMap<>(cachedKeyValues.size(), 1);
        cachedKeyValues.forEach((k, v) -> result.put(k, fromStoreValue(v)));
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<Object, Object> getAll(Iterable<?> keys, Function<? super Set<?>, ? extends Map<?, ?>> mappingFunction) {
        CacheGetContext context = CacheGetContext.saveRedisAbsentKeys((Iterable<Object>) keys);
        doGetAll(context);
        long redisAbsentCount = context.redisAbsentCount;
        Map<Object, Object> cachedKeyValues = context.cachedKeyValues;
        if (redisAbsentCount == 0) {
            // 所有 key 全部命中缓存
            Map<Object, Object> result = new HashMap<>(cachedKeyValues.size(), 1);
            cachedKeyValues.forEach((k, v) -> result.put(k, fromStoreValue(v)));
            return result;
        }
        // 从 mappingFunction 中获取值
        Map<?, ?> mappingKeyValues = mappingFunction.apply(context.redisAbsentKeys);
        putAll(mappingKeyValues);
        Map<Object, Object> result = new HashMap<>(cachedKeyValues.size() + mappingKeyValues.size(), 1);
        cachedKeyValues.forEach((k, v) -> result.put(k, fromStoreValue(v)));
        result.putAll(mappingKeyValues);
        return result;
    }


    private void doGetAll(CacheGetContext context) {
        context.cachedKeyValues = caffeineCache.getAll(
                context.allKeys,
                keyIterable -> context.fetchFromRedis(redisStringRepository, this::getKey)
        );
    }


    @Override
    public void put(@NonNull Object key, Object value) {
        if (!super.isAllowNullValues() && value == null) {
            this.evict(key);
            return;
        }
        doPut(key, value);
    }


    @Override
    public void invalidate(Object key) {
        evict(key);
    }

    @Override
    public void invalidateAll(Iterable<? extends Object> keys) {
        List<?> keysColl = Lists.newArrayList(keys);
        List<String> redisKeys = keysColl.stream().map(this::getKey).toList();
        redisStringRepository.removeAll(redisKeys);
        push(keysColl, CacheOperation.EVICT_BATCH);
        caffeineCache.invalidateAll(keysColl);
    }


    @Nullable
    @Override
    protected Object lookup(@NonNull Object key) {
        String cacheName = key.toString();
        String cacheKey = getKey(cacheName);
        Object value = getL1Value(cacheName);
        if (!Objects.isNull(value)) {
            log.debug("get cache from caffeine, the key is : {}", cacheKey);
            return value;
        }

        value = getL2Value(cacheKey);

        if (value != null) {
            log.debug("get cache from redis and put in caffeine, the key is : {}", cacheKey);
            setL1Value(cacheName, value);
        } else {
            put(key, null);
        }
        return value;
    }

    public void setL1Value(Object key, Object value) {
        caffeineCache.put(key, value);
    }

    public void setL1Value(Map.Entry<String, Object> entry) {
        caffeineCache.put(entry.getKey(), entry.getValue());
    }

    @Override
    public Object getL2Value(String key) {
        return redisStringRepository.get(key);
    }

    @Override
    public Object getL1Value(Object key) {
        return caffeineCache.getIfPresent(key);
    }

    @Override
    public void clearLocalBatch(Iterable<Object> keys) {
        log.debug("clear local cache, the keys is : {}", keys);
        caffeineCache.invalidateAll(keys);
    }

    @Override
    public void clearLocal(Object key) {
        log.debug("clear local cache, the key is : {}", key);
        if (key == null) {
            caffeineCache.invalidateAll();
        }
        else {
            caffeineCache.invalidate(key);
        }
    }


    protected String getKey(Object key) {
        return this.getKeyPrefix + key;
    }


    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        Object value = lookup(key);
        if (Objects.nonNull(value)) {
            return (T) value;
        }

        String lockName = "lock:" + name;
        boolean b = false;
        try {
            b = redisStringRepository.tryLock(lockName, 100);
            value = lookup(key);
            if (Objects.nonNull(value)) {
                return (T) value;
            }
            value = valueLoader.call();
            Object storeValue = toStoreValue(value);
            put(key, storeValue);
            return (T) value;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (b) {
                redisStringRepository.unlock(lockName);
            }
        }
    }


    @Override
    public void putAll(Map<? extends Object, ?> map) {
        Map<String, Object> temp = map
                                    .entrySet()
                                    .stream()
                                    .collect(Collectors.toMap(k -> k.getKey().toString(), e -> toStoreValue(e.getValue())));

        redisStringRepository.batchPut(temp, this::getKey, this::getExpire, this::setL1Value);
        push(new ArrayList<>(temp.keySet()), CacheOperation.EVICT_BATCH);
    }

    @Override
    public void evict(@NonNull Object key) {
        redisStringRepository.remove(getKey(key));

        push(key, CacheOperation.EVICT_BATCH);

        caffeineCache.invalidate(key);
    }

    @Override
    public void clear() {
        // 先清除redis中缓存数据，然后清除caffeine中的缓存，避免短时间内如果先清除caffeine缓存后其他请求会再从redis里加载到caffeine中

        redisStringRepository.removeAll(this.name);

        push(null, CacheOperation.EVICT_BATCH);

        caffeineCache.invalidateAll();
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

    @NonNull
    @Override
    public Object getNativeCache() {
        return this;
    }

    @Override
    public void cleanUp() {
        caffeineCache.cleanUp();
    }

    protected Duration getExpire(Object value) {
        Duration cacheNameExpire = expires.get(this.name);
        if (cacheNameExpire == null) {
            cacheNameExpire = defaultExpiration;
        }
        if ((value == null || value == NullValue.INSTANCE) && this.defaultNullValuesExpiration != null) {
            cacheNameExpire = this.defaultNullValuesExpiration;
        }
        return cacheNameExpire;
    }


    private void doPut(Object key, Object value) {
        value = toStoreValue(value);
        Duration expire = getExpire(value);
        setL2Value(key, value, expire);

        push(key, CacheOperation.EVICT);

        setL1Value(key, value);
    }

    protected void push(Object key, CacheOperation operation) {
        push(new EvictEvent(this.serverId, this.name, operation, key));
    }

    protected void push(EvictEvent message) {
        eventSubPubProvider.publish(topic, message);
    }

    public void setL2Value(Object key, Object value, Duration expire) {
        if (!expire.isNegative() && !expire.isZero()) {
            redisStringRepository.put(getKey(key), value, expire);
        }
        else {
            redisStringRepository.put(getKey(key), value);
        }
    }

    @Override
    public ValueWrapper putIfAbsent(@NonNull Object key, Object value) {
        String lockName = "lock:" + name;
        Object prevValue;
        boolean b = false;
        // 考虑使用分布式锁，或者将redis的setIfAbsent改为原子性操作
        try {
            b = redisStringRepository.tryLock(lockName, 100);
            prevValue = getL2Value(key.toString());
            if (prevValue == null) {
                doPut(key, value);
            }
            return toValueWrapper(prevValue);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (b) {
                redisStringRepository.unlock(lockName);
            }
        }
    }

    @Override
    public Policy<Object, Object> policy() {
        return caffeineCache.policy();
    }

    @NonNull
    @Override
    public String getName() {
        return name;
    }
}
