package com.zjj.security.tenant.component;

import com.zjj.autoconfigure.component.cache.CacheType;
import com.zjj.autoconfigure.component.tenant.MultiTenancyProperties;
import com.zjj.security.tenant.component.configuration.TenantJwtCacheSelector;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月25日 11:30
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@EnableConfigurationProperties(MultiTenancyProperties.class)
@Import(TenantJwtCacheSelector.class)
public @interface EnableTenantJwtCache {

    CacheType cache() default CacheType.REDIS;
}
