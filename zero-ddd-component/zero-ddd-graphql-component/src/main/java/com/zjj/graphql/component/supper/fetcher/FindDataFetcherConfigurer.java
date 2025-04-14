package com.zjj.graphql.component.supper.fetcher;

import com.zjj.domain.component.BaseRepository;
import com.zjj.graphql.component.context.EntityContext;
import com.zjj.graphql.component.query.fuzzy.QuerydslFuzzyDataFetcher;
import graphql.schema.DataFetcher;
import graphql.schema.idl.RuntimeWiring;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.graphql.data.query.QuerydslDataFetcher;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月09日 10:22
 */
public record FindDataFetcherConfigurer(List<BaseRepository<?, ?>> repositories, EntityContext entityContext) implements RuntimeWiringConfigurer {

    @Override
    public void configure(RuntimeWiring.Builder builder) {
        builder.type("Query", typeWiring -> {
            for (BaseRepository<?, ?> repository : repositories) {
                EntityType<?> type = entityContext.entity(repository);
                typeWiring.dataFetcher("query" + type, QuerydslDataFetcher.builder(repository).many());
                typeWiring.dataFetcher("find" + type, QuerydslDataFetcher.builder(repository).single());
                typeWiring.dataFetcher("page" + type.getName(), QuerydslDataFetcher.builder(repository).scrollable());
            }
            return typeWiring;
        });
    }
}
