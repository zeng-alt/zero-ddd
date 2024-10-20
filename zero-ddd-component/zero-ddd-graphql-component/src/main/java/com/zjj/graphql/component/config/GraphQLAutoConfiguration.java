package com.zjj.graphql.component.config;


import com.zjj.graphql.component.supper.*;
import com.zjj.graphql.component.supper.definition.EntityTypeDefinitionConfigurer;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.graphql.execution.TypeDefinitionConfigurer;

/**
 * @author zengJiaJun
 * @crateTime 2024年07月15日 21:56
 * @version 1.0
 */
@AutoConfiguration(after = EntityManager.class)
public class GraphQLAutoConfiguration {

    @Bean
    public RuntimeWiringConfigurer graphQlTypeConfiguration() {
        return new GraphQlExtendedScalarsConfigurer();
    }

    @Bean
    public GraphQlSourceBuilderCustomizer autoGenGraphqlBuilderCustomizer(ObjectProvider<TypeDefinitionConfigurer> configurers) {
        return new AutoGenGraphqlCustomizer(configurers);
    }

}