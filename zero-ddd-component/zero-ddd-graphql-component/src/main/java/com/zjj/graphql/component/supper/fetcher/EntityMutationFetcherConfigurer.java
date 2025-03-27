package com.zjj.graphql.component.supper.fetcher;

import com.zjj.graphql.component.context.EntityContext;
import com.zjj.graphql.component.mutation.MutationDataFetcher;
import com.zjj.domain.component.BaseRepository;
import graphql.schema.idl.RuntimeWiring;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月25日 11:11
 */
public record EntityMutationFetcherConfigurer(List<BaseRepository> repositories, EntityContext entityContext, TransactionTemplate template) implements RuntimeWiringConfigurer {
    @Override
    public void configure(RuntimeWiring.Builder builder) {
        builder.type("Mutation", typeWiring -> {
            for (BaseRepository repository : repositories) {
                EntityType<?> type = entityContext.entity(repository);
                typeWiring.dataFetcher("save" + type, MutationDataFetcher.builder(repository, template).save(entityContext.getSaveHandlers(type.getName())));
                typeWiring.dataFetcher("delete" + type + "Ids", MutationDataFetcher.builder(repository, template).deleteId());
            }
            return typeWiring;
        });
    }

}
