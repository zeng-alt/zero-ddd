package com.zjj.graphql.component.supper.fetcher;

import com.querydsl.core.util.BeanUtils;
import com.zjj.graphql.component.context.EntityContext;
import com.zjj.graphql.component.query.condition.QuerydslConditionDataFetcher;
import com.zjj.graphql.component.query.fuzzy.QuerydslFuzzyDataFetcher;
import com.zjj.graphql.component.supper.BaseRepository;
import com.zjj.graphql.component.utils.RepositoryUtils;
import graphql.schema.idl.RuntimeWiring;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月22日 21:47
 */
public record ConditionDataFetcherConfigurer(List<BaseRepository> repositories, EntityContext entityContext) implements RuntimeWiringConfigurer {

    @Override
    public void configure(RuntimeWiring.Builder builder) {
        builder.type("Query", typeWiring -> {
            for (BaseRepository repository : repositories) {
                EntityType<?> type = entityContext.entity(repository);

                typeWiring.dataFetcher("conditionQuery" + type, QuerydslConditionDataFetcher.builder(repository).many());
                typeWiring.dataFetcher("conditionFind" + type, QuerydslConditionDataFetcher.builder(repository).single());
                typeWiring.dataFetcher("conditionPage" + type, QuerydslConditionDataFetcher.builder(repository).scrollable());
            }
            return typeWiring;
        });
    }
}
