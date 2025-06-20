package com.zjj.security.abac.component;

import java.lang.annotation.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月04日 10:40
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ObjectReturn {

    String value();

    String name();

    String[] argument();

    Class<?> returnType();
}
