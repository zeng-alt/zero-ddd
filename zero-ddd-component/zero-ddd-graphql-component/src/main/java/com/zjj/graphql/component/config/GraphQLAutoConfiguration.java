package com.zjj.graphql.component.config;


import com.zjj.autoconfigure.component.graphql.ExcludeTypeProvider;
import com.zjj.graphql.component.context.ConditionTypeContext;
import com.zjj.graphql.component.context.EntityContext;
import com.zjj.graphql.component.spi.EntitySaveHandler;
import com.zjj.graphql.component.supper.*;
import com.zjj.graphql.component.supper.definition.EntityTypeDefinitionConfigurer;
import jakarta.persistence.EntityManager;
import org.hibernate.engine.internal.EntityEntryContext;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.graphql.execution.TypeDefinitionConfigurer;

import java.util.List;

/**
 * @author zengJiaJun
 * @crateTime 2024年07月15日 21:56
 * @version 1.0
 */
@AutoConfiguration(after = EntityManager.class)
public class GraphQLAutoConfiguration {

    @Bean
    public ConditionTypeContext conditionTypeContext() {
        return new ConditionTypeContext();
    }

    @Bean
    public RuntimeWiringConfigurer graphQlTypeConfiguration() {
        return new GraphQlExtendedScalarsConfigurer();
    }

    @Bean
    @ConditionalOnMissingBean
    public EntityContext entityContext(EntityManager entityManager, ObjectProvider<ExcludeTypeProvider> excludeTypeProviders, List<EntitySaveHandler<?>> saveHandlerList) {
        return new EntityContext(entityManager, excludeTypeProviders, saveHandlerList);
    }

    @Bean
    public GraphQlSourceBuilderCustomizer autoGenGraphqlBuilderCustomizer(ObjectProvider<TypeDefinitionConfigurer> configurers) {
        return new AutoGenGraphqlCustomizer(configurers);
    }


    @Bean
    public GraphQLExceptionHandler graphQLExceptionHandler() {
        return new GraphQLExceptionHandler();
    }
}
