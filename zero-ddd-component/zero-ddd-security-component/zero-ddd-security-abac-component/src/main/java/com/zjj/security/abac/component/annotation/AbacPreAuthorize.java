package com.zjj.security.abac.component.annotation;

import com.zjj.autoconfigure.component.security.abac.HttpResourceType;
import com.zjj.autoconfigure.component.security.abac.ResourceType;
import com.zjj.autoconfigure.component.security.abac.ResourceType1;

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
