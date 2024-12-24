package com.zjj.tenant.datasource.component;

import com.zjj.autoconfigure.component.cache.CacheType;
import com.zjj.autoconfigure.component.tenant.MultiTenancyProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月23日 21:52
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@EnableConfigurationProperties(MultiTenancyProperties.class)
@Import(TenantSelector.class)
public @interface EnableMultiTenancy {

    TenantMode mode() default TenantMode.DATABASE;

    CacheType cache() default CacheType.REDIS;

//    ConditionalOnWebApplication.Type type() default ConditionalOnWebApplication.Type.SERVLET;
}
