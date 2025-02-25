package com.zjj.l2.cache.component.supper;

import com.zjj.autoconfigure.component.l2cache.L2CacheManage;

import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月25日 21:53
 */
public interface L2RedissonCaffeineCacheManage extends L2CacheManage {

    public void registerCustomCache(String name, com.github.benmanes.caffeine.cache.Cache<Object, Object> cache);


    public void registerCustomCache(String name, RedissonCaffeineCache.Builder builder);

    public void setCacheNamePrefix(UnaryOperator<String> prefix);
}
