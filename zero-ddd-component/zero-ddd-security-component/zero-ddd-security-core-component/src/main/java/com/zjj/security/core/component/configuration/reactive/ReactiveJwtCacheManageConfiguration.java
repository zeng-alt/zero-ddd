package com.zjj.security.core.component.configuration.reactive;

import com.zjj.autoconfigure.component.l2cache.L2CacheManage;
import com.zjj.autoconfigure.component.redis.RedisStringRepository;
import com.zjj.autoconfigure.component.security.jwt.JwtCacheManage;
import com.zjj.autoconfigure.component.security.jwt.JwtProperties;
import com.zjj.autoconfigure.component.security.jwt.ReactiveJwtCacheManage;
import com.zjj.cache.component.config.RedisAutoConfiguration;
import com.zjj.security.core.component.supper.DefaultJwtCacheManage;
import com.zjj.security.core.component.supper.DefaultJwtL2CacheManage;
import com.zjj.security.core.component.supper.reactive.DefaultReactiveJwtCacheManage;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月26日 21:15
 */
@AutoConfiguration
public class ReactiveJwtCacheManageConfiguration {

    @Bean
    @ConditionalOnMissingBean(value = {ReactiveJwtCacheManage.class, L2CacheManage.class}, type = "com.zjj.security.tenant.component.configuration.ReactiveTenantJwtCacheSelector")
    @ConditionalOnBean(RedisAutoConfiguration.class)
    public ReactiveJwtCacheManage defaultJwtCacheManage(RedisStringRepository redisStringRepository, JwtProperties jwtProperties) {
        return new DefaultReactiveJwtCacheManage(redisStringRepository, jwtProperties);
    }


//    @Bean
//    @ConditionalOnMissingBean(value = ReactiveJwtCacheManage.class, type = "com.zjj.security.tenant.component.configuration.ReactiveTenantJwtCacheSelector")
//    @ConditionalOnBean(L2CacheManage.class)
//    public ReactiveJwtCacheManage defalutL2JwtCacheManage(L2CacheManage l2CacheManage) {
//        return new DefaultJwtL2CacheManage(l2CacheManage);
//    }
}
