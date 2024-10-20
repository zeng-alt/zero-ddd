package com.zjj.graphql.component.config;

import com.zjj.graphql.component.supper.definition.EntityInputDefinitionConfigurer;
import com.zjj.graphql.component.supper.definition.SortDefinitionConfigurer;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月18日 20:25
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({EntityInputDefinitionConfigurer.class, SortDefinitionConfigurer.class})
public @interface EnableGenEntityInput {
}
