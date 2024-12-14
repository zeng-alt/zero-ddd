package com.zjj.security.abac.component.configuration;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.zjj.autoconfigure.component.security.abac.AbacCacheManage;
import com.zjj.security.abac.component.annotation.AbacPreAuthorize;
import com.zjj.security.abac.component.supper.AbacMethodSecurityExpressionHandler;
import com.zjj.security.abac.component.supper.AbacPreAuthorizationManager;
import com.zjj.security.abac.component.supper.AbacPreAuthorizeExpressionAttributeRegistry;
import com.zjj.security.abac.component.supper.SpelDeserializer;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.Pointcuts;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;
import org.springframework.expression.Expression;
import org.springframework.security.authorization.method.AuthorizationInterceptorsOrder;
import org.springframework.security.authorization.method.AuthorizationManagerAfterMethodInterceptor;
import org.springframework.security.authorization.method.AuthorizationManagerBeforeMethodInterceptor;

import java.lang.annotation.Annotation;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月12日 21:29
 */
@AutoConfiguration
public class AbacAutoConfiguration {
    @Bean
    public Module spelModule(){
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(Expression.class, new SpelDeserializer());
        return simpleModule;
    }

    @Bean
    @ConditionalOnMissingBean(AbacMethodSecurityExpressionHandler.class)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public AbacMethodSecurityExpressionHandler abacMethodSecurityExpressionHandler() {
        return new AbacMethodSecurityExpressionHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public AbacPreAuthorizeExpressionAttributeRegistry abacPreAuthorizeExpressionAttributeRegistry(AbacMethodSecurityExpressionHandler abacMethodSecurityExpressionHandler, AbacCacheManage abacCacheManage) {
        return new AbacPreAuthorizeExpressionAttributeRegistry(abacMethodSecurityExpressionHandler, abacCacheManage);
    }

    // TODO
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public AbacPreAuthorizationManager abacPreMyAuthorizationManager(AbacPreAuthorizeExpressionAttributeRegistry abacPreAuthorizeExpressionAttributeRegistry) {
//        return new AbacPreAuthorizationManager(abacPreAuthorizeExpressionAttributeRegistry);
        return null;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public Advisor preAuthorizeMethodInterceptor(AbacPreAuthorizationManager abacPreAuthorizationManager) {
        ComposablePointcut pointcut = new ComposablePointcut(classOrMethod(AbacPreAuthorize.class));
        AuthorizationManagerBeforeMethodInterceptor interceptor = new AuthorizationManagerBeforeMethodInterceptor(
                pointcut, abacPreAuthorizationManager);
        interceptor.setOrder(AuthorizationInterceptorsOrder.PRE_AUTHORIZE.getOrder());
        return interceptor;
    }

    private static Pointcut classOrMethod(Class<? extends Annotation> annotation) {
        return Pointcuts.union(new AnnotationMatchingPointcut(null, annotation, true),
                new AnnotationMatchingPointcut(annotation, true));
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public Advisor postAuthorizeMethodInterceptor() {
        return AuthorizationManagerAfterMethodInterceptor.postAuthorize();
    }
}
