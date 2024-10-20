package com.zjj.graphql.component.supper.fetcher;

import com.querydsl.core.util.BeanUtils;
import com.zjj.graphql.component.query.fuzzy.QuerydslFuzzyDataFetcher;
import com.zjj.graphql.component.supper.BaseRepository;
import com.zjj.graphql.component.utils.RepositoryUtils;
import graphql.schema.idl.RuntimeWiring;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author zengJiaJun
 * @crateTime 2024年10月19日 21:09
 * @version 1.0
 */
public record FuzzyDataFetcherConfigurer(List<BaseRepository> repositories) implements RuntimeWiringConfigurer {


    @Override
    public void configure(RuntimeWiring.Builder builder) {
        builder.type("Query", typeWiring -> {
            for (BaseRepository repository : repositories) {
                String type = RepositoryUtils.getGraphQlTypeName(repository);
                if (!StringUtils.hasText(type)) continue;
                String capitalize = BeanUtils.capitalize(type);
                typeWiring.dataFetcher("fuzzyQuery" + capitalize, QuerydslFuzzyDataFetcher.builder(repository).many());
                typeWiring.dataFetcher("fuzzyFind" + capitalize, QuerydslFuzzyDataFetcher.builder(repository).single());
                typeWiring.dataFetcher("fuzzyPage" + capitalize, QuerydslFuzzyDataFetcher.builder(repository).scrollable());
            }
            return typeWiring;
        });
    }
}
