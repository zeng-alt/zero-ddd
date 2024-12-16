package com.zjj.security.abac.component.configuration;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.zjj.autoconfigure.component.security.abac.AbacCacheManage;
import com.zjj.autoconfigure.component.security.abac.PolicyDefinition;
import com.zjj.autoconfigure.component.security.abac.PolicyRule;
import com.zjj.security.abac.component.advice.AbacExceptionAdvice;
import com.zjj.security.abac.component.annotation.AbacPreAuthorize;
import com.zjj.security.abac.component.spi.EnvironmentAttribute;
import com.zjj.security.abac.component.spi.ObjectAttribute;
import com.zjj.security.abac.component.supper.*;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.Pointcuts;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;
import org.springframework.expression.Expression;
import org.springframework.security.authorization.method.AuthorizationInterceptorsOrder;
import org.springframework.security.authorization.method.AuthorizationManagerAfterMethodInterceptor;
import org.springframework.security.authorization.method.AuthorizationManagerBeforeMethodInterceptor;
import org.springframework.security.authorization.method.MethodAuthorizationDeniedHandler;

import java.lang.annotation.Annotation;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月12日 21:29
 */
@AutoConfiguration
@EnableConfigurationProperties(PolicyProperties.class)
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
    public AbacMethodSecurityExpressionHandler abacMethodSecurityExpressionHandler(ObjectProvider<EnvironmentAttribute> environmentAttributes) {
        AbacMethodSecurityExpressionHandler handler = new AbacMethodSecurityExpressionHandler();
        handler.setEnvironmentAttributes(environmentAttributes.stream().toList());
        return handler;
    }

    @Bean
    @ConditionalOnMissingBean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public AbacPreAuthorizeExpressionAttributeRegistry abacPreAuthorizeExpressionAttributeRegistry(AbacMethodSecurityExpressionHandler abacMethodSecurityExpressionHandler, PolicyProperties properties, ObjectProvider<PolicyDefinition> policyDefinition, ObjectProvider<ObjectAttribute> objectAttributes) {
//        return new AbacPreAuthorizeExpressionAttributeRegistry(abacMethodSecurityExpressionHandler, policyDefinition.getIfAvailable(() -> new JsonFilePolicyDefinition(properties.getFilePath())), objectAttributes);
        return new AbacPreAuthorizeExpressionAttributeRegistry(abacMethodSecurityExpressionHandler, new JsonFilePolicyDefinition(properties.getFilePath()), objectAttributes);
    }

    // TODO
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public AbacPreAuthorizationManager abacPreMyAuthorizationManager(AbacPreAuthorizeExpressionAttributeRegistry abacPreAuthorizeExpressionAttributeRegistry) {
        return new AbacPreAuthorizationManager(abacPreAuthorizeExpressionAttributeRegistry);
//        return null;
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

    @Bean
    public AbacExceptionAdvice abacExceptionAdvice() {
        return new AbacExceptionAdvice();
    }

    @Bean
    public DefaultEnvironmentAttribute defaultEnvironmentAttribute() {
        return new DefaultEnvironmentAttribute();
    }
}
