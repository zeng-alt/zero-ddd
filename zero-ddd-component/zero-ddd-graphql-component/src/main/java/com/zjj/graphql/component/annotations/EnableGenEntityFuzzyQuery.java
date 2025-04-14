package com.zjj.graphql.component.annotations;

import com.zjj.graphql.component.supper.definition.EntityFuzzyQueryDefinitionConfigurer;
import com.zjj.graphql.component.supper.definition.EntityQueryDefinitionConfigurer;
import com.zjj.graphql.component.supper.fetcher.FuzzyDataFetcherConfigurer;
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
@Import({EntityFuzzyQueryDefinitionConfigurer.class, FuzzyDataFetcherConfigurer.class})
public @interface EnableGenEntityFuzzyQuery {
}
