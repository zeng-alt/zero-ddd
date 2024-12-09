package com.zjj.security.rbac.component.config;

import com.zjj.autoconfigure.component.security.rbac.RbacCacheManage;
import com.zjj.security.rbac.component.parse.*;
import com.zjj.security.rbac.component.supper.reactive.ReactiveRbacAccessAuthorizationManager;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月26日 21:33
 */
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class RbacReactiveAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean(ReactiveHttpResourceLocator.class)
    public ReactiveHttpResourceLocator reactiveHttpResourceLocator(RbacCacheManage rbacCacheManage) {
        return new ReactiveHttpResourceLocator(rbacCacheManage);
    }

    @Bean
    @ConditionalOnMissingBean(ReactiveGraphqlResourceLocator.class)
    public ReactiveGraphqlResourceLocator reactiveGraphqlResourceLocator() {
        return new ReactiveGraphqlResourceLocator();
    }

    @Bean
    @ConditionalOnMissingBean(ReactiveGraphqlResourceHandler.class)
    public ReactiveGraphqlResourceHandler reactiveGraphqlResourceHandler(ReactiveResourceQueryManager reactiveResourceQueryManager) {
        return new ReactiveGraphqlResourceHandler(reactiveResourceQueryManager);
    }

    @Bean
    @ConditionalOnMissingBean(ReactiveResourceQueryManager.class)
    public ReactiveResourceQueryManager reactiveResourceQueryManager(ObjectProvider<ReactiveResourceLocator> reactiveResourceLocators) {
        return new ReactiveResourceQueryManager(reactiveResourceLocators.stream().toList());
    }

    @Bean
    public ReactiveParseManager reactiveParseManager(ObjectProvider<ReactiveResourceHandler> reactiveResourceHandlers, ReactiveResourceQueryManager reactiveResourceQueryManager) {
        List<ReactiveResourceHandler> list = new ArrayList<>(reactiveResourceHandlers.orderedStream().toList());
        list.add(new ReactiveHttpResourceHandler(reactiveResourceQueryManager));
        return new ReactiveParseManager(list, new ReactiveHttpResourceHandler(reactiveResourceQueryManager));
    }


    @Bean
    public ReactiveRbacAccessAuthorizationManager rbacReactiveAuthorizationManager(ReactiveParseManager reactiveParseManager) {
        return new ReactiveRbacAccessAuthorizationManager(reactiveParseManager);
    }


//    @Bean
//    public ServerHttpSecurityBuilderCustomizer rabcServerHttpSecurityBuilderCustomizer(ReactiveAuthorizationManager<AuthorizationContext> rbacReactiveAuthorizationManager) {
//        return http -> http.authorizeExchange(exchange -> exchange.anyExchange().access(rbacReactiveAuthorizationManager));
////        return http -> http.authorizeExchange(exchange -> exchange.anyExchange().access( AuthenticatedReactiveAuthorizationManager.authenticated()));
//    }
}
