package com.zjj.graphql.component.mutation;

import com.querydsl.core.types.EntityPath;
import com.zjj.domain.component.BaseRepository;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.core.ResolvableType;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.util.TypeInformation;
import org.springframework.graphql.execution.SelfDescribingDataFetcher;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月25日 11:32
 */
public class DeleteEntityFetcher<T, ID> extends MutationDataFetcher<T, ID> implements SelfDescribingDataFetcher<String> {

    private final BaseRepository<T, ID> executor;

    public DeleteEntityFetcher(
            BaseRepository<T, ID> executor, TypeInformation<T> domainType, Class<T> resultType, Class<ID> idType,
            QuerydslBinderCustomizer<? extends EntityPath<T>> customizer, TransactionTemplate template) {

        super(domainType, idType, (QuerydslBinderCustomizer) customizer, template);
        this.executor = executor;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public ResolvableType getReturnType() {
        return ResolvableType.forType(String.class);
    }


    @Override
    public String get(DataFetchingEnvironment environment) throws Exception {
        T t = buildEntity(environment);
        this.executor.delete(t);
        return "ok";
    }
}
