package com.zjj.graphql.component.config;

import java.lang.annotation.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月18日 20:31
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableGenEntityMutation
@EnableGenEntityInput
@EnableGenEntityQuery
@EnableGenEntityType
public @interface EnableGenEntityAll {
}
