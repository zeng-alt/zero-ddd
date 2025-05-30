package com.zjj.security.rbac.client.component;

import com.zjj.autoconfigure.component.redis.RedisSubPubRepository;
import com.zjj.security.rbac.client.component.actuator.RbacClientEndpoint;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.GraphQlSource;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月07日 15:54
 */
@AutoConfiguration
public class RbacClientAutoConfiguration {

    @Bean
    public RouteTemplateSupper routeTemplateSupper(EndpointPrefix endpointPrefix, RedisSubPubRepository redisSubPubRepository) {
        return new RouteTemplateSupper(endpointPrefix, redisSubPubRepository);
    }


    @Bean
    @ConditionalOnMissingBean
    public EndpointPrefix endpointPrefix() {
        return () -> "/";
    }


    @Bean
    @ConditionalOnMissingBean
    RbacClientEndpoint rbacClientEndpoint(RouteTemplateSupper routeTemplateSupper) {
        return RbacClientEndpoint.precomputed(routeTemplateSupper);
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(GraphQlSource.class)
    static class GraphqlResourceConfig {

        @Bean
        public GraphqlTemplateSupper graphqlTemplateSupper(EndpointPrefix endpointPrefix, RedisSubPubRepository redisSubPubRepository) {
            return new GraphqlTemplateSupper(endpointPrefix, redisSubPubRepository);
        }
    }
}
