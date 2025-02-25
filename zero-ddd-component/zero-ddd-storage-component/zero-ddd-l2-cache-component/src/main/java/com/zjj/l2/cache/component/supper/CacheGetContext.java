package com.zjj.l2.cache.component.supper;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.zjj.autoconfigure.component.redis.RedisStringRepository;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月16日 14:31
 */
public class CacheGetContext {

    public CacheGetContext(Iterable<Object> allKeys) {
        this.allKeys = allKeys;
    }

    protected Iterable<Object> allKeys;

    /**
     * 是否将redis未查询到的key保存到 {@link #redisAbsentKeys}
     */
    protected boolean saveRedisAbsentKeys = false;

    /**
     * redis中未查询到的key
     */
    protected Set<Object> redisAbsentKeys;

    /**
     * redis中未查询到的key数量
     */
    protected long redisAbsentCount;

    /**
     * caffeine和redis中缓存的键值，未经过{@link #fromStoreValue}转换
     */
    protected Map<Object, Object> cachedKeyValues;

    public static CacheGetContext saveRedisAbsentKeys(Iterable<Object> allKeys) {
        CacheGetContext cacheGetContext = new CacheGetContext(allKeys);
        cacheGetContext.saveRedisAbsentKeys = true;
        return cacheGetContext;
    }

    public static CacheGetContext notSaveRedisAbsentKeys(Iterable<Object> allKeys) {
        CacheGetContext cacheGetContext = new CacheGetContext(allKeys);
        cacheGetContext.saveRedisAbsentKeys = false;
        return cacheGetContext;
    }


    /**
     * 从Redis批量获取数据并处理结果
     *
     * @param redisStringRepository Redis操作库
     * @param keyFunction           将对象转换为Redis键的函数
     * @return 处理后的结果Map
     */
    public Map<Object, Object> fetchFromRedis(RedisStringRepository redisStringRepository, Function<Object, String> keyFunction) {
        List<Object> caffeineAbsentKeys = Lists.newArrayList(allKeys);
        List<String> redisKeys = convertKeys(caffeineAbsentKeys, keyFunction);
        List<Object> redisValues = fetchRedisValues(redisStringRepository, redisKeys);
        int redisAbsentCount = countRedisAbsentValues(redisValues);
        this.redisAbsentCount = redisAbsentCount;

        Map<Object, Object> result = new HashMap<>(caffeineAbsentKeys.size() - redisAbsentCount, 1);
        if (saveRedisAbsentKeys) {
            this.redisAbsentKeys = Sets.newHashSetWithExpectedSize(redisAbsentCount);
        }

        populateResultMap(caffeineAbsentKeys, redisValues, result);

        return result;
    }


    private List<String> convertKeys(List<Object> keys, Function<Object, String> keyFunction) {
        return keys.stream()
                .map(keyFunction)
                .collect(Collectors.toList());
    }

    private List<Object> fetchRedisValues(RedisStringRepository redisStringRepository, List<String> redisKeys) {
        try {
            return redisStringRepository.getAll(redisKeys);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch values from Redis", e);
        }
    }

    private int countRedisAbsentValues(List<Object> redisValues) {
        return (int) redisValues.stream().filter(Objects::isNull).count();
    }

    private void populateResultMap(List<Object> caffeineAbsentKeys, List<Object> redisValues, Map<Object, Object> result) {
        for (int i = 0; i < caffeineAbsentKeys.size(); i++) {
            Object key = caffeineAbsentKeys.get(i);
            Object redisValue = redisValues.get(i);
            if (redisValue != null) {
                result.put(key, redisValue);
            } else if (saveRedisAbsentKeys) {
                this.redisAbsentKeys.add(key);
            }
        }
    }
}
