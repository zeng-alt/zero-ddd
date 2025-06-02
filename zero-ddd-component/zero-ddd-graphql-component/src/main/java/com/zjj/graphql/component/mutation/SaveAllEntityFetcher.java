package com.zjj.graphql.component.mutation;

import com.querydsl.core.types.EntityPath;
import com.zjj.bean.componenet.BeanHelper;
import com.zjj.domain.component.BaseEntity;
import com.zjj.domain.component.BaseRepository;
import com.zjj.domain.component.TransactionCallbackResult;
import com.zjj.graphql.component.spi.EntitySaveHandler;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.core.ResolvableType;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.util.TypeInformation;
import org.springframework.graphql.execution.SelfDescribingDataFetcher;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月25日 11:31
 */
public class SaveAllEntityFetcher<T, ID> extends MutationDataFetcher<T, ID> implements SelfDescribingDataFetcher<Iterable<T>> {
    private final Class<T> type;
    private final BaseRepository<T, ID> executor;
    private final List<EntitySaveHandler<T>> handlers;

    public SaveAllEntityFetcher(
            BaseRepository<T, ID> executor, TypeInformation<T> domainType, Class<T> resultType, Class<ID> idType,
            QuerydslBinderCustomizer<? extends EntityPath<T>> customizer, TransactionTemplate template,
            List<EntitySaveHandler<T>> handlers) {

        super(domainType, idType, (QuerydslBinderCustomizer) customizer, template);
        this.type = resultType;
        this.executor = executor;
        this.handlers = handlers;
    }

    @Override
    public ResolvableType getReturnType() {
        return ResolvableType.forClass(this.type);
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
    public Iterable<T> get(DataFetchingEnvironment environment) throws Exception {
        List<T> list = buildListEntity(environment);
        Map<ID, BaseEntity> oldEntity = list.stream().filter(t -> t instanceof BaseEntity baseEntity && !baseEntity.isNew()).map(e -> (BaseEntity) e).collect(Collectors.toMap(e -> (ID) e.getId(), e -> e));
        List<T> newEntity = new ArrayList<>(list.stream().filter(t -> t instanceof BaseEntity baseEntity && baseEntity.isNew()).toList());
        Set<ID> ids = oldEntity.keySet();
        Object ignoringNull = environment.getArgument("ignoringNull");
        return template.execute((TransactionCallbackResult<List<T>>) () -> {
            if (Boolean.TRUE.equals(ignoringNull)) {
                executor.findByIdIn(ids).forEach(e -> {
                    if (e instanceof BaseEntity<?>  baseEntity) {
                        BaseEntity old = oldEntity.get(baseEntity.getId());
                        BeanHelper.copyPropertiesIgnoringNull(old, baseEntity);
                        newEntity.add((T) baseEntity);
                    }
                });
            }
            executor.saveAll(newEntity);
            return newEntity;
        });
    }
}
