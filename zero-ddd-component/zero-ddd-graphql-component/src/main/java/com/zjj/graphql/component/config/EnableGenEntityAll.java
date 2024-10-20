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
@EnableGenEntityInput
@EnableGenEntityType
@EnableGenEntityQuery
@EnableGenEntityMutation
@EnableGenEntityFuzzyQuery
public @interface EnableGenEntityAll {
}
