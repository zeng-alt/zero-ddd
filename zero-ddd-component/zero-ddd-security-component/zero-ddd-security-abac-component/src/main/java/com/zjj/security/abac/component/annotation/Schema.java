package com.zjj.security.abac.component.annotation;

import java.lang.annotation.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月10日 18:22
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Schema {

    String name();

    String description() default "";
}
