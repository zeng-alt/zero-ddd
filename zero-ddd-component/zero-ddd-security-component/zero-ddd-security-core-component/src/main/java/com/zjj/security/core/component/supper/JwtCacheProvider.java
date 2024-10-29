package com.zjj.security.core.component.supper;

import com.zjj.autoconfigure.component.l2cache.L2CacheManage;
import com.zjj.autoconfigure.component.l2cache.provider.L2CacheManageProvider;
import com.zjj.autoconfigure.component.security.jwt.JwtProperties;

import com.zjj.l2.cache.component.provider.L2BuilderProvider;
import com.zjj.l2.cache.component.supper.RedissonCaffeineCache;
import com.zjj.l2.cache.component.supper.RedissonCaffeineCacheManage;
import com.zjj.memory.component.provider.Tuple;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.temporal.TemporalUnit;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月25日 14:06
 */
@RequiredArgsConstructor
public class JwtCacheProvider implements L2CacheManageProvider {

    private final JwtProperties jwtProperties;


    @Override
    public void consumer(L2CacheManage l2CacheManage) {
        RedissonCaffeineCache.Builder builder = RedissonCaffeineCache.builder();
        Long expiration = jwtProperties.getExpiration();
        TemporalUnit temporalUnit = jwtProperties.getTemporalUnit();
        Duration expire = Duration.of(expiration, temporalUnit);
        builder
                .name("jwt")
                .cacheNullValue(false)
                .l1(consumer -> consumer
                        .maximumSize(10000)
                        .expireAfterWrite(Duration.of(expiration - 1, temporalUnit))
                        .initialCapacity(100)
                        .recordStats()
                ).l2(consumer -> consumer
                        .expire(expire)
                );

        if (l2CacheManage instanceof RedissonCaffeineCacheManage manage) {
            manage.registerCustomCache("jwt", builder);
        }
    }
}
