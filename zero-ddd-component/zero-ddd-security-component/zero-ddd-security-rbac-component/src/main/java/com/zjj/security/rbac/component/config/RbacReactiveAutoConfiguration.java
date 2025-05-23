package com.zjj.security.rbac.component.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zjj.autoconfigure.component.security.rbac.RbacCacheManage;
import com.zjj.security.core.component.spi.ReactiveAuthorizationManagerProvider;
import com.zjj.security.rbac.component.handler.ReactiveGraphqlResourceHandler;
import com.zjj.security.rbac.component.handler.ReactiveHttpResourceHandler;
import com.zjj.security.rbac.component.handler.ReactiveResourceHandler;
import com.zjj.security.rbac.component.locator.ReactiveGraphqlResourceLocator;
import com.zjj.security.rbac.component.locator.ReactiveHttpResourceLocator;
import com.zjj.security.rbac.component.locator.ReactivePermissionLocator;
import com.zjj.security.rbac.component.locator.ReactiveResourceLocator;
import com.zjj.security.rbac.component.manager.ReactiveAdminAuthorizationManager;
import com.zjj.security.rbac.component.manager.ReactiveParseManager;
import com.zjj.security.rbac.component.manager.ReactiveResourceQueryManager;
import com.zjj.security.rbac.component.manager.ReactiveRbacAccessAuthorizationManager;
import com.zjj.security.rbac.component.router.RouteTemplateManager;
import com.zjj.security.rbac.component.spi.ReactiveGraphqlWhiteListAuthorizationManager;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.security.web.server.authorization.AuthorizationContext;

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
    public ReactivePermissionLocator reactivePermissionLocator(RbacCacheManage rbacCacheManage) {
        return new ReactivePermissionLocator(rbacCacheManage);
    }


    @Bean
    @ConditionalOnMissingBean(ReactiveHttpResourceLocator.class)
    public ReactiveHttpResourceLocator reactiveHttpResourceLocator(ObjectProvider<RbacCacheManage> rbacCacheManage) {
        return new ReactiveHttpResourceLocator(rbacCacheManage.getIfAvailable());
    }

    @Bean
    @ConditionalOnMissingBean(ReactiveGraphqlResourceLocator.class)
    public ReactiveGraphqlResourceLocator reactiveGraphqlResourceLocator(ObjectProvider<RbacCacheManage> rbacCacheManage) {
        return new ReactiveGraphqlResourceLocator(rbacCacheManage.getIfAvailable());
    }

    @Bean
    @ConditionalOnMissingBean(ReactiveGraphqlResourceHandler.class)
    public ReactiveGraphqlResourceHandler reactiveGraphqlResourceHandler(
            ReactiveResourceQueryManager reactiveResourceQueryManager,
            ObjectMapper objectMapper,
            ReactivePermissionLocator permissionLocator,
            ReactiveGraphqlWhiteListAuthorizationManager reactiveGraphqlWhiteListAuthorizationManager
    ) {
        return new ReactiveGraphqlResourceHandler(reactiveResourceQueryManager, objectMapper, permissionLocator, reactiveGraphqlWhiteListAuthorizationManager);
    }

    @Bean
    @ConditionalOnMissingBean(ReactiveResourceQueryManager.class)
    public ReactiveResourceQueryManager reactiveResourceQueryManager(ObjectProvider<ReactiveResourceLocator> reactiveResourceLocators) {
        return new ReactiveResourceQueryManager(reactiveResourceLocators.orderedStream().toList());
    }

    @Bean
    public ReactiveParseManager reactiveParseManager(ObjectProvider<ReactiveResourceHandler> reactiveResourceHandlers, ReactiveResourceQueryManager reactiveResourceQueryManager, ReactivePermissionLocator permissionLocator, RouteTemplateManager routeTemplateManager) {
        List<ReactiveResourceHandler> list = new ArrayList<>(reactiveResourceHandlers.orderedStream().toList());
        return new ReactiveParseManager(list, new ReactiveHttpResourceHandler(reactiveResourceQueryManager, routeTemplateManager, permissionLocator));
    }


//    @Bean
//    @Order(10)
//    public ReactiveRbacAccessAuthorizationManager rbacReactiveAuthorizationManager(ReactiveParseManager reactiveParseManager) {
//        return new ReactiveRbacAccessAuthorizationManager(reactiveParseManager);
//    }
//
//    @Bean
//    @Order(5)
//    public ReactiveAdminAuthorizationManager reactiveAdminAuthorizationManager(ReactiveParseManager reactiveParseManager) {
//        return new ReactiveAdminAuthorizationManager();
//    }

    @Bean
    @Order(10)
    public ReactiveAuthorizationManagerProvider<AuthorizationContext> rbacReactiveAuthorizationManager(ReactiveParseManager reactiveParseManager) {
        return () -> new ReactiveRbacAccessAuthorizationManager(reactiveParseManager);
    }

    @Bean
    @Order(5)
    public ReactiveAuthorizationManagerProvider<AuthorizationContext> reactiveAdminAuthorizationManager() {
        return ReactiveAdminAuthorizationManager::new;
    }


//    @Bean
//    public ServerHttpSecurityBuilderCustomizer rabcServerHttpSecurityBuilderCustomizer(ReactiveAuthorizationManager<AuthorizationContext> rbacReactiveAuthorizationManager) {
//        return http -> http.authorizeExchange(exchange -> exchange.anyExchange().access(rbacReactiveAuthorizationManager));
////        return http -> http.authorizeExchange(exchange -> exchange.anyExchange().access( AuthenticatedReactiveAuthorizationManager.authenticated()));
//    }
}
