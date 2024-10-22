package com.zjj.graphql.component.supper.fetcher;

import com.querydsl.core.util.BeanUtils;
import com.zjj.graphql.component.query.condition.QuerydslConditionDataFetcher;
import com.zjj.graphql.component.query.fuzzy.QuerydslFuzzyDataFetcher;
import com.zjj.graphql.component.supper.BaseRepository;
import com.zjj.graphql.component.utils.RepositoryUtils;
import graphql.schema.idl.RuntimeWiring;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月22日 21:47
 */
public record ConditionDataFetcherConfigurer(List<BaseRepository> repositories) implements RuntimeWiringConfigurer {

    @Override
    public void configure(RuntimeWiring.Builder builder) {
        builder.type("Query", typeWiring -> {
            for (BaseRepository repository : repositories) {
                String type = RepositoryUtils.getGraphQlTypeName(repository);
                if (!StringUtils.hasText(type)) continue;
                String capitalize = BeanUtils.capitalize(type);
                typeWiring.dataFetcher("conditionQuery" + capitalize, QuerydslConditionDataFetcher.builder(repository).many());
                typeWiring.dataFetcher("conditionFind" + capitalize, QuerydslConditionDataFetcher.builder(repository).single());
                typeWiring.dataFetcher("conditionPage" + capitalize, QuerydslConditionDataFetcher.builder(repository).scrollable());
            }
            return typeWiring;
        });
    }
}
