package com.zjj.security.abac.component.configuration;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.zjj.autoconfigure.component.security.abac.EnvironmentAttribute;
import com.zjj.autoconfigure.component.security.abac.ObjectAttribute;
import com.zjj.autoconfigure.component.security.abac.PolicyDefinition;
import com.zjj.security.abac.component.advice.AbacExceptionAdvice;
import com.zjj.security.abac.component.annotation.AbacPostAuthorize;
import com.zjj.security.abac.component.annotation.AbacPreAuthorize;
import com.zjj.security.abac.component.supper.*;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.Pointcuts;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;
import org.springframework.expression.Expression;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authorization.method.AuthorizationInterceptorsOrder;
import org.springframework.security.authorization.method.AuthorizationManagerAfterMethodInterceptor;
import org.springframework.security.authorization.method.AuthorizationManagerBeforeMethodInterceptor;

import java.lang.annotation.Annotation;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月12日 21:29
 */
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
    public AbacMethodSecurityExpressionHandler abacMethodSecurityExpressionHandler(ObjectProvider<EnvironmentAttribute> environmentAttributes, ObjectProvider<PermissionEvaluator> permissionEvaluator) {
        AbacMethodSecurityExpressionHandler handler = new AbacMethodSecurityExpressionHandler();
        handler.setEnvironmentAttributes(environmentAttributes.stream().toList());
        permissionEvaluator.ifAvailable(handler::setPermissionEvaluator);
        return handler;
    }

    @Bean
    @ConditionalOnMissingBean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public AbacPreAuthorizeExpressionAttributeRegistry abacPreAuthorizeExpressionAttributeRegistry(AbacMethodSecurityExpressionHandler abacMethodSecurityExpressionHandler, PolicyProperties properties, ObjectProvider<PolicyDefinition> policyDefinition, ObjectProvider<ObjectAttribute> objectAttributes) {
//        return new AbacPreAuthorizeExpressionAttributeRegistry(abacMethodSecurityExpressionHandler, policyDefinition.getIfAvailable(() -> new JsonFilePolicyDefinition(properties.getFilePath())), objectAttributes);
        return new AbacPreAuthorizeExpressionAttributeRegistry(abacMethodSecurityExpressionHandler, new JsonFilePolicyDefinition(properties.getPreFilePath()), objectAttributes);
    }

    // TODO
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public AbacPreAuthorizationManager abacPreMyAuthorizationManager(AbacPreAuthorizeExpressionAttributeRegistry abacPreAuthorizeExpressionAttributeRegistry) {
        return new AbacPreAuthorizationManager(abacPreAuthorizeExpressionAttributeRegistry);
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

    /*********************************************
     * 后置授权
     * *******************************************
     */

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public Advisor postAuthorizeMethodInterceptor(AbacPostAuthorizationManager abacPostAuthorizationManager) {
        ComposablePointcut pointcut = new ComposablePointcut(classOrMethod(AbacPostAuthorize.class));
        AuthorizationManagerAfterMethodInterceptor interceptor = new AuthorizationManagerAfterMethodInterceptor(
                pointcut, abacPostAuthorizationManager);
        interceptor.setOrder(AuthorizationInterceptorsOrder.POST_AUTHORIZE.getOrder());
        return interceptor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public AbacPostAuthorizationManager abacPostAuthorizationManager(AbacPostAuthorizeExpressionAttributeRegistry abacPostAuthorizeExpressionAttributeRegistry) {
        return new AbacPostAuthorizationManager(abacPostAuthorizeExpressionAttributeRegistry);
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public AbacPostAuthorizeExpressionAttributeRegistry abacPostAuthorizeExpressionAttributeRegistry(MethodSecurityExpressionHandler expressionHandler, PolicyProperties properties, ObjectProvider<ObjectAttribute> objectAttributes, PolicyDefinition policyDefinition) {
        //        return new AbacPostAuthorizeExpressionAttributeRegistry(abacMethodSecurityExpressionHandler, policyDefinition.getIfAvailable(() -> new JsonFilePolicyDefinition(properties.getFilePath())), objectAttributes);
        return new AbacPostAuthorizeExpressionAttributeRegistry(expressionHandler, new JsonFilePolicyDefinition(properties.getPostFilePath()), objectAttributes);
    }

    @Bean
    public AbacExceptionAdvice abacExceptionAdvice() {
        return new AbacExceptionAdvice();
    }

    @Bean
    public DefaultEnvironmentAttribute defaultEnvironmentAttribute() {
        return new DefaultEnvironmentAttribute();
    }


//    @RouterOperations({
//            @RouterOperation(
//                    path = "/model/building/{entId}/stations",
//                    beanClass = AbacModelHandler.class,
//                    beanMethod = "getStations",
//                    method = RequestMethod.GET,
//                    operation = @Operation(
//                            operationId = "getStations",
//                            parameters = @Parameter(
//                                    name = "entId",
//                                    in = ParameterIn.PATH,
//                                    required = true,
//                                    description = "企业ID"
//                            )
//                    )
//            ),
//            @RouterOperation(
//                    path = "/model/building/devices/points/real-time-data",
//                    beanClass = AbacModelHandler.class,
//                    beanMethod = "getRealTimeData",
//                    operation = @Operation(
//                            operationId = "getRealTimeData",
//                            requestBody = @RequestBody(
//                                    required = true,
//                                    description = "请求体",
//                                    content = @Content(
//                                            schema = @Schema(implementation = RealTimeDataQueryVO.class)
//                                    )
//                            )
//                    )
//            )
//    })
//    @Bean
//    public RouterFunction<ServerResponse> abacRouterFunction(AbacModelHandler abacModelHandler) {
//        return RouterFunctions.nest(path("/abac"),
//                    RouterFunctions.route(GET("/{routkey}"), abacModelHandler::getStations)
//                );
//    }

    private static Pointcut classOrMethod(Class<? extends Annotation> annotation) {
        return Pointcuts.union(new AnnotationMatchingPointcut(null, annotation, true),
                new AnnotationMatchingPointcut(annotation, true));
    }
}
