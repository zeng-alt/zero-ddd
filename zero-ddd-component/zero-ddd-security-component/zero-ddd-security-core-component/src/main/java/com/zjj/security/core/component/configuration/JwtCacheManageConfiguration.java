package com.zjj.security.core.component.configuration;

import com.zjj.autoconfigure.component.l2cache.L2CacheManage;
import com.zjj.autoconfigure.component.redis.RedisStringRepository;
import com.zjj.autoconfigure.component.security.jwt.JwtCacheManage;
import com.zjj.autoconfigure.component.security.jwt.JwtProperties;
import com.zjj.cache.component.config.RedisAutoConfiguration;
import com.zjj.security.core.component.supper.DefaultJwtCacheManage;
import com.zjj.security.core.component.supper.DefaultJwtL2CacheManage;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月26日 15:14
 */
@AutoConfiguration
public class JwtCacheManageConfiguration {

    @Bean
    @ConditionalOnMissingBean(value = {JwtCacheManage.class, L2CacheManage.class}, type = "com.zjj.security.tenant.component.configuration.TenantJwtCacheSelector")
    @ConditionalOnBean(RedisAutoConfiguration.class)
    public JwtCacheManage defaultJwtCacheManage(RedisStringRepository redisStringRepository, JwtProperties jwtProperties) {
        return new DefaultJwtCacheManage(redisStringRepository, jwtProperties);
    }


    @Bean
    @ConditionalOnMissingBean(value = JwtCacheManage.class, type = "com.zjj.security.tenant.component.configuration.TenantJwtCacheSelector")
    @ConditionalOnBean(L2CacheManage.class)
    public JwtCacheManage defalutL2JwtCacheManage(L2CacheManage l2CacheManage) {
        return new DefaultJwtL2CacheManage(l2CacheManage);
    }
}
