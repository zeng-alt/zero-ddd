package com.zjj.graphql.component.config;

import com.zjj.graphql.component.supper.definition.EntityMutationDefinitionConfigurer;
import com.zjj.graphql.component.supper.definition.EntityQueryDefinitionConfigurer;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月18日 20:26
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(EntityMutationDefinitionConfigurer.class)
public @interface EnableGenEntityMutation {
}
