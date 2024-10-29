package com.zjj.security.tenant.component.configuration;

import com.zjj.security.tenant.component.enums.CacheType;
import com.zjj.tenant.component.config.TenantAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月25日 21:54
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({SecurityTenantAutoConfiguration.class, TenantAutoConfiguration.class})
public @interface EnableTenant {
    CacheType cacheType() default CacheType.REDIS;
}
