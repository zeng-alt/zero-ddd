//package com.zjj.security.tenant.component.supper;
//
//import com.zjj.autoconfigure.component.l2cache.L2CacheManage;
//import com.zjj.autoconfigure.component.l2cache.provider.L2CacheManageProvider;
//import com.zjj.autoconfigure.component.security.jwt.JwtProperties;
//import com.zjj.autoconfigure.component.tenant.TenantService;
//import com.zjj.l2.cache.component.supper.RedissonCaffeineCache;
//import com.zjj.l2.cache.component.supper.RedissonCaffeineCacheManage;
//import lombok.RequiredArgsConstructor;
//
//import java.time.Duration;
//import java.time.temporal.TemporalUnit;
//
///**
// * @author zengJiaJun
// * @version 1.0
// * @crateTime 2024年10月25日 15:11
// */
//@RequiredArgsConstructor
//public class L2JwtCacheProvider implements L2CacheManageProvider {
//
//    private final JwtProperties jwtProperties;
//    private final TenantService tenantService;
//
//    @Override
//    public void consumer(L2CacheManage l2CacheManage) {
//        RedissonCaffeineCache.Builder builder = RedissonCaffeineCache.builder();
//        Long expiration = jwtProperties.getExpiration();
//        TemporalUnit temporalUnit = jwtProperties.getTemporalUnit();
//        Duration expire = Duration.of(expiration, temporalUnit);
//        builder
//                .cacheNullValue(false)
//                .l1(consumer -> consumer
//                        .maximumSize(10000)
//                        .expireAfterWrite(Duration.of(expiration - 1, temporalUnit))
//                        .initialCapacity(100)
//                        .recordStats()
//                ).l2(consumer -> consumer
//                        .expire(expire)
//                );
//
//
//        for (String tenant : tenantService.getTenants()) {
//            String name = tenant + ":jwt";
//            builder.name(name);
//            if (l2CacheManage instanceof RedissonCaffeineCacheManage manage) {
//                manage.registerCustomCache(name, builder);
//            }
//        }
//    }
//}
