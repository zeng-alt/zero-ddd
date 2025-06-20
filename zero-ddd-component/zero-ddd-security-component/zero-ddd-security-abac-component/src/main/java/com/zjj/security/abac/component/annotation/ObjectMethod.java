package com.zjj.security.abac.component.annotation;

import java.lang.annotation.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月04日 10:38
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ObjectMethod {
    String value();

//    String argument();

//    String name();
}
