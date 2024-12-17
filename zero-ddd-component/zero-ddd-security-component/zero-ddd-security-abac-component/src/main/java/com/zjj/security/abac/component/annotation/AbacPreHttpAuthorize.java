package com.zjj.security.abac.component.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月17日 21:55
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@AbacPreAuthorize
public @interface AbacPreHttpAuthorize {

    @AliasFor(annotation = AbacPreAuthorize.class)
    String value();
}
