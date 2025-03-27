package com.zjj.graphql.component.annotations;

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
@EnableGenEntityConditionQuery
public @interface EnableGenEntityAll {
}
