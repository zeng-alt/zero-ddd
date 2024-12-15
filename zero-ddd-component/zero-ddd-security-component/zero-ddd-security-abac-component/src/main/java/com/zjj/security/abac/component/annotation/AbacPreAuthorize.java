package com.zjj.security.abac.component.annotation;


import java.lang.annotation.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月12日 21:05
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface AbacPreAuthorize {

    /**
     * @return 权限的key
     */
    String value();

    String resourceType() default "http";
}
