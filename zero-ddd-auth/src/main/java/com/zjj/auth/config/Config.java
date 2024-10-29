package com.zjj.auth.config;

import com.zjj.autoconfigure.component.l2cache.L2CacheManage;
//import com.zjj.security.tenant.component.supper.TenantL2JwtCacheManage;
//import com.zjj.tenant.component.spi.TenantContextHolder;
import com.zjj.autoconfigure.component.redis.RedisStringRepository;
import com.zjj.autoconfigure.component.security.jwt.JwtCacheManage;
import com.zjj.autoconfigure.component.security.jwt.JwtProperties;
//import com.zjj.security.core.component.supper.DefaultJwtCacheManage;
//import com.zjj.security.tenant.component.supper.TenantJwtL2CacheManage;
//import com.zjj.tenant.component.spi.TenantContextHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月25日 16:03
 */
@Configuration
public class Config {

//    @Bean
//    public TenantL2JwtCacheManage jwtCacheManage(L2CacheManage l2CacheManage, TenantContextHolder tenantContextHolder) {
//        return new TenantL2JwtCacheManage(l2CacheManage, tenantContextHolder);
//    }

//    @Bean
//    public JwtCacheManage jwtCacheManage(L2CacheManage l2CacheManage, TenantContextHolder tenantContextHolder) {
//        return new TenantJwtL2CacheManage(l2CacheManage, tenantContextHolder);
//    }
}
