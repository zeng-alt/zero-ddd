package com.zjj.security.tenant.component;

import com.zjj.autoconfigure.component.cache.CacheType;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface EnableTenantFilter {

    CacheType cache() default CacheType.REDIS;
}