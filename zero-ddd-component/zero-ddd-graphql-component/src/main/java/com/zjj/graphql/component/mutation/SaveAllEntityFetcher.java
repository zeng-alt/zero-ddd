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
 * @crateTime 2025年03月25日 11:31
 */
public class SaveAllEntityFetcher<T, ID> extends MutationDataFetcher<T, ID> implements SelfDescribingDataFetcher<T> {
    private final Class<T> type;
    private final BaseRepository<T, ID> executor;

    public SaveAllEntityFetcher(
            BaseRepository<T, ID> executor, TypeInformation<T> domainType, Class<T> resultType, Class<ID> idType,
            QuerydslBinderCustomizer<? extends EntityPath<T>> customizer, TransactionTemplate template) {

        super(domainType, idType, (QuerydslBinderCustomizer) customizer, template);
        this.type = resultType;
        this.executor = executor;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public ResolvableType getReturnType() {
        return null;
    }

    /**
     * This is called by the graphql engine to fetch the value.  The {@link DataFetchingEnvironment} is a composite
     * context object that tells you all you need to know about how to fetch a data value in graphql type terms.
     *
     * @param environment this is the data fetching environment which contains all the context you need to fetch a value
     * @return a value of type T. May be wrapped in a {@link DataFetcherResult}
     * @throws Exception to relieve the implementations from having to wrap checked exceptions. Any exception thrown
     *                   from a {@code DataFetcher} will eventually be handled by the registered {@link DataFetcherExceptionHandler}
     *                   and the related field will have a value of {@code null} in the result.
     */
    @Override
    public T get(DataFetchingEnvironment environment) throws Exception {
        return null;
    }
}
