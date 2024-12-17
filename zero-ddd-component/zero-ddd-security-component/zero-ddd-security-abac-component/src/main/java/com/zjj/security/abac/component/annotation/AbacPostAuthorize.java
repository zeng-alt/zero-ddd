package com.zjj.security.abac.component.annotation;

import java.lang.annotation.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月12日 20:11
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface AbacPostAuthorize {

    /**
     * @return 权限的key
     */
    String value() default "";

    String resourceType() default "http";
}
