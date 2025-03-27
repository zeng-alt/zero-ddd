package com.zjj.graphql.component.mutation;

import com.querydsl.core.types.EntityPath;
import com.zjj.domain.component.BaseRepository;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.core.ResolvableType;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.util.TypeInformation;
import org.springframework.graphql.execution.SelfDescribingDataFetcher;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月25日 11:31
 */
public class DeleteEntityByIdFetcher<T, ID> extends MutationDataFetcher<T, ID> implements SelfDescribingDataFetcher<String> {

    private final BaseRepository<T, ID> executor;

    public DeleteEntityByIdFetcher(
            BaseRepository<T, ID> executor, TypeInformation<T> domainType, Class<T> resultType, Class<ID> idType,
            QuerydslBinderCustomizer<? extends EntityPath<T>> customizer, TransactionTemplate template) {

        super(domainType, idType, (QuerydslBinderCustomizer) customizer, template);
        this.executor = executor;
    }

    @Override
    public ResolvableType getReturnType() {
        return ResolvableType.forClass(String.class);
    }

    @Override
    public String get(DataFetchingEnvironment environment) throws Exception {
        Map<String, Object> arguments = environment.getArguments();
        if (arguments.size() > 1) {
            throw new IllegalArgumentException("deleteById only support one argument");
        }
        QuerydslBindings bindings = new QuerydslBindings();
        List<ID> entityId = (List<ID>) ENTITY_BUILDER.getEntityId(super.domainType, arguments, bindings);
        this.executor.deleteAllById(entityId);
        return "ok";
    }
}
