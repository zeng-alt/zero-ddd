package com.zjj.tenant.datasource.component;


import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年01月01日 21:10
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface SwitchTenant {

    @AliasFor("tenant")
    String value();

    @AliasFor("value")
    String tenant() default "";

    String database() default "";

    String schema() default "";
}
