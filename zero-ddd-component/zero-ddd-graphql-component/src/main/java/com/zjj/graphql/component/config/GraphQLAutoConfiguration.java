package com.zjj.graphql.component.config;


import com.zjj.graphql.component.supper.AutoGenGraphqlBuilderCustomizer;
import com.zjj.graphql.component.supper.AutoGenGraphqlConfiguration;
import com.zjj.graphql.component.supper.GraphQlTypeConfiguration;
import com.zjj.graphql.component.supper.GraphQlTypeSourceBuilderCustomizer;
import jakarta.persistence.EntityManager;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

/**
 * @author zengJiaJun
 * @crateTime 2024年07月15日 22:56
 * @version 1.0
 */
@AutoConfiguration(after = EntityManager.class)
public class GraphQLAutoConfiguration {

    @Bean
    public RuntimeWiringConfigurer graphQlTypeConfiguration() {
        return new GraphQlTypeConfiguration();
    }

    @Bean
    public RuntimeWiringConfigurer autoGenGraphqlConfiguration() {
        return new AutoGenGraphqlConfiguration();
    }


    @Order(1)
    @Bean
    public GraphQlSourceBuilderCustomizer graphQlTypeSourceBuilderCustomizer() {
        return new GraphQlTypeSourceBuilderCustomizer();
    }

    @Order(2)
    @Bean GraphQlSourceBuilderCustomizer autoGenGraphqlBuilderCustomizer(EntityManager entityManager) {
        return new AutoGenGraphqlBuilderCustomizer(entityManager);
    }
}
