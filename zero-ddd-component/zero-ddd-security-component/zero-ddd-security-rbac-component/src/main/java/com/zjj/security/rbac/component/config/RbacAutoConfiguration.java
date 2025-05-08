package com.zjj.security.rbac.component.config;

import com.zjj.autoconfigure.component.redis.RedisSubPubRepository;
import com.zjj.autoconfigure.component.security.WhiteListProperties;
import com.zjj.autoconfigure.component.security.rbac.GraphqlResource;
import com.zjj.autoconfigure.component.security.rbac.Resource;
import com.zjj.security.rbac.component.manager.DefaultReactiveGraphqlWhiteListAuthorizationManager;
import com.zjj.security.rbac.component.router.RouteTemplateManager;
import com.zjj.security.rbac.component.spi.GraphqlWhiteListService;
import com.zjj.security.rbac.component.spi.ReactiveGraphqlWhiteListAuthorizationManager;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月26日 21:40
 */
@AutoConfiguration
public class RbacAutoConfiguration {

    @Bean
    public RouteTemplateManager routeTemplateManager(RedisSubPubRepository redisSubPubRepository) {
        return new RouteTemplateManager(redisSubPubRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public GraphqlWhiteListService defaultGraphqlWhiteListService(WhiteListProperties whiteListProperties) {
        Set<GraphqlResource> ignoreResource = whiteListProperties.getIgnoreResource();
        return () -> ignoreResource;
    }

    @Bean
    @ConditionalOnMissingBean
    public ReactiveGraphqlWhiteListAuthorizationManager reactiveGraphqlWhiteListAuthorizationManager(GraphqlWhiteListService graphqlWhiteListService) {
        return new DefaultReactiveGraphqlWhiteListAuthorizationManager(graphqlWhiteListService);
    }
}
