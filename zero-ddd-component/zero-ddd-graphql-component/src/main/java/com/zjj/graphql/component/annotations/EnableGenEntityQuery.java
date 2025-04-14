package com.zjj.graphql.component.annotations;

import com.zjj.graphql.component.supper.definition.EntityQueryDefinitionConfigurer;
import com.zjj.graphql.component.supper.fetcher.EntityMutationFetcherConfigurer;
import com.zjj.graphql.component.supper.fetcher.FindDataFetcherConfigurer;
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
@Import({EntityQueryDefinitionConfigurer.class, FindDataFetcherConfigurer.class})
public @interface EnableGenEntityQuery {
}
