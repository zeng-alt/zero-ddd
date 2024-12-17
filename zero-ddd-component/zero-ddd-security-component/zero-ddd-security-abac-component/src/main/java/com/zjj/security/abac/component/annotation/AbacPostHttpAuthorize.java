package com.zjj.security.abac.component.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.lang.annotation.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月17日 21:50
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@AbacPostAuthorize
public @interface AbacPostHttpAuthorize {

    @AliasFor(annotation = AbacPostAuthorize.class)
    String value();
}
