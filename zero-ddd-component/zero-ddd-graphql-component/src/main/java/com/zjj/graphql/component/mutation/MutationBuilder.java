package com.zjj.graphql.component.mutation;

import com.querydsl.core.types.EntityPath;
import com.zjj.domain.component.BaseRepository;
import com.zjj.graphql.component.spi.EntitySaveHandler;
import graphql.schema.DataFetcher;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.util.TypeInformation;
import org.springframework.graphql.data.pagination.CursorStrategy;
import org.springframework.lang.Nullable;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import java.util.List;


/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月25日 17:03
 */
public class MutationBuilder<T, ID, R> {

    private static final QuerydslBinderCustomizer NO_OP_BINDER_CUSTOMIZER = (bindings, root) -> {
    };

    private final BaseRepository<T, ID> executor;

    private final TypeInformation<T> domainType;

    private final Class<R> resultType;

    private final TransactionTemplate template;

    private final Class<ID> idType;

    @Nullable
    private final CursorStrategy<ScrollPosition> cursorStrategy;

    private final QuerydslBinderCustomizer<? extends EntityPath<T>> customizer;

    @SuppressWarnings("unchecked")
    public MutationBuilder(BaseRepository<T, ID> executor, Class<T> domainType, Class<ID> idType, TransactionTemplate template) {
        this(executor, TypeInformation.of(domainType),
                (Class<R>) domainType, idType, template, null, NO_OP_BINDER_CUSTOMIZER);
    }


    public MutationBuilder(BaseRepository<T, ID> executor, TypeInformation<T> domainType, Class<R> resultType,
                        Class<ID> idType, TransactionTemplate template,
                        @Nullable CursorStrategy<ScrollPosition> cursorStrategy,
                        QuerydslBinderCustomizer<? extends EntityPath<T>> customizer) {

        this.executor = executor;
        this.domainType = domainType;
        this.resultType = resultType;
        this.cursorStrategy = cursorStrategy;
        this.customizer = customizer;
        this.idType = idType;
        this.template = template;
    }


    public <P> MutationBuilder<T, ID, P> projectAs(Class<P> projectionType) {
        Assert.notNull(projectionType, "Projection type must not be null");
        return new MutationBuilder<>(this.executor, this.domainType, projectionType,
                this.idType, this.template, this.cursorStrategy, this.customizer);
    }

    public MutationBuilder<T, ID, R> customizer(QuerydslBinderCustomizer<? extends EntityPath<T>> customizer) {
        Assert.notNull(customizer, "QuerydslBinderCustomizer must not be null");
        return new MutationBuilder<>(this.executor, this.domainType, this.resultType,
                this.idType, this.template, this.cursorStrategy, customizer);
    }


    public DataFetcher<T> save(List<EntitySaveHandler<T>> handlers) {
        return new SaveEntityFetcher<>(this.executor, this.domainType, (Class<T>) this.resultType, this.idType, this.customizer, this.template, handlers);
    }

    public DataFetcher<Iterable<T>> saveAll(List<EntitySaveHandler<T>> handlers) {
        return new SaveAllEntityFetcher<>(this.executor, this.domainType, (Class<T>) this.resultType, this.idType, this.customizer, this.template, handlers);
    }

    public DataFetcher<T> save() {
        return save(List.of());
    }


    public DataFetcher<String> deleteId() {
        return new DeleteEntityByIdFetcher<>(this.executor, this.domainType, this.domainType.getType(), this.idType, this.customizer, this.template);
    }

    public DataFetcher<String> delete() {
        return new DeleteEntityFetcher<>(this.executor, this.domainType, this.domainType.getType(), this.idType, this.customizer, this.template);
    }
}
