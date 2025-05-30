package com.zjj.graphql.component.mutation;

import com.querydsl.core.types.EntityPath;
import com.zjj.bean.componenet.BeanHelper;
import com.zjj.domain.component.BaseEntity;
import com.zjj.domain.component.BaseRepository;
import com.zjj.domain.component.TransactionCallbackResult;
import com.zjj.graphql.component.spi.EntitySaveHandler;
import graphql.schema.DataFetchingEnvironment;
import io.vavr.control.Option;
import org.springframework.core.ResolvableType;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.util.TypeInformation;
import org.springframework.graphql.execution.SelfDescribingDataFetcher;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月25日 11:31
 */
public class SaveEntityFetcher<T, ID> extends MutationDataFetcher<T, ID> implements SelfDescribingDataFetcher<T> {
    private final Class<T> type;
    private final BaseRepository<T, ID> executor;

    private final List<EntitySaveHandler<T>> handlers;

    public SaveEntityFetcher(
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

    @Override
    public T get(DataFetchingEnvironment environment) throws Exception {
        final T t = buildEntity(environment);
        Object ignoringNull = environment.getArgument("ignoringNull");
        return template.execute((TransactionCallbackResult<T>) () -> {
            T temp = t;
            if (t instanceof BaseEntity baseEntity && !baseEntity.isNew() && Boolean.TRUE.equals(ignoringNull)) {
                Option<T> option = executor.findById((ID) baseEntity.getId());
                temp = option.map(e -> {
                    BeanHelper.copyPropertiesIgnoringNull(t, e);
                    return e;
                }).getOrElse(t);
            }
            for (EntitySaveHandler<T> handler : handlers) {
                handler.handler(temp);
            }
            return executor.save(temp);
        });
    }
}
