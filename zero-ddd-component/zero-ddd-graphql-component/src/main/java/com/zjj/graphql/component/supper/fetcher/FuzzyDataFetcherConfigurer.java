package com.zjj.graphql.component.supper.fetcher;

import com.zjj.graphql.component.context.EntityContext;
import com.zjj.graphql.component.query.fuzzy.QuerydslFuzzyDataFetcher;
import com.zjj.domain.component.BaseRepository;
import graphql.schema.idl.RuntimeWiring;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import java.util.List;

/**
 * @author zengJiaJun
 * @crateTime 2024年10月19日 21:09
 * @version 1.0
 */
public record FuzzyDataFetcherConfigurer(List<BaseRepository<?, ?>> repositories, EntityContext entityContext) implements RuntimeWiringConfigurer {


    @Override
    public void configure(RuntimeWiring.Builder builder) {
        builder.type("Query", typeWiring -> {
            for (BaseRepository<?, ?> repository : repositories) {
                EntityType<?> type = entityContext.entity(repository);
                typeWiring.dataFetcher("fuzzyQuery" + type, QuerydslFuzzyDataFetcher.builder(repository).many());
                typeWiring.dataFetcher("fuzzyFind" + type, QuerydslFuzzyDataFetcher.builder(repository).single());
                typeWiring.dataFetcher("fuzzyPage" + type, QuerydslFuzzyDataFetcher.builder(repository).scrollable());
            }
            return typeWiring;
        });
    }
}
