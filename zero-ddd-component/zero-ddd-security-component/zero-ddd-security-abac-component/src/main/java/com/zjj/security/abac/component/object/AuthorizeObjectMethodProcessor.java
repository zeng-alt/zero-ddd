package com.zjj.security.abac.component.object;

import com.zjj.autoconfigure.component.json.JsonHelper;
import com.zjj.autoconfigure.component.security.abac.AbacContextJsonEntity;
import com.zjj.bean.componenet.BeanHelper;
import com.zjj.security.abac.component.ObjectReturn;
import com.zjj.security.abac.component.annotation.AbacPreAuthorize;
import com.zjj.security.abac.component.annotation.ObjectMethod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.autoproxy.AutoProxyUtils;
import org.springframework.aop.scope.ScopedObject;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月04日 10:48
 */
@Slf4j
@RequiredArgsConstructor
public class AuthorizeObjectMethodProcessor implements SmartInitializingSingleton, BeanFactoryPostProcessor, ApplicationContextAware {

    private final AuthorizeObjectManager manager;
    @Nullable
    private ConfigurableApplicationContext applicationContext;
    @Nullable
    private ConfigurableListableBeanFactory beanFactory;
    private final AbacMappingHandlerMapping abacMappingHandlerMapping;
    private final JsonHelper jsonHelper;
    private final Set<Class<?>> nonAnnotatedClasses = ConcurrentHashMap.newKeySet(64);
    @Override
    public void afterSingletonsInstantiated() {
        ConfigurableListableBeanFactory beanFactory = this.beanFactory;
        Assert.state(beanFactory != null, "No ConfigurableListableBeanFactory set");
        String[] beanNames = beanFactory.getBeanNamesForType(Object.class);
        for (String beanName : beanNames) {
            if (!ScopedProxyUtils.isScopedTarget(beanName)) {
                Class<?> type = null;
                try {
                    type = AutoProxyUtils.determineTargetClass(beanFactory, beanName);
                }
                catch (Throwable ex) {
                    // An unresolvable bean type, probably from a lazy bean - let's ignore it.
                    if (log.isDebugEnabled()) {
                        log.debug("Could not resolve target class for bean with name '" + beanName + "'", ex);
                    }
                }
                if (type != null) {
                    if (ScopedObject.class.isAssignableFrom(type)) {
                        try {
                            Class<?> targetClass = AutoProxyUtils.determineTargetClass(
                                    beanFactory, ScopedProxyUtils.getTargetBeanName(beanName));
                            if (targetClass != null) {
                                type = targetClass;
                            }
                        }
                        catch (Throwable ex) {
                            // An invalid scoped proxy arrangement - let's ignore it.
                            if (log.isDebugEnabled()) {
                                log.debug("Could not resolve target bean for scoped proxy '" + beanName + "'", ex);
                            }
                        }
                    }
                    try {
                        processPreAuthorizeObject(beanName, type);
                        processPostAuthorizeObject(beanName, type);
                        processBean(beanName, type);
                    }
                    catch (Throwable ex) {
                        throw new BeanInitializationException("Failed to process @EventListener " +
                                "annotation on bean with name '" + beanName + "': " + ex.getMessage(), ex);
                    }
                }
            }
        }

        this.abacMappingHandlerMapping.send();
    }

    private void processPostAuthorizeObject(String beanName, Class<?> type) {

    }

    private void processPreAuthorizeObject(String beanName, Class<?> targetType) {
        if (!this.nonAnnotatedClasses.contains(targetType) &&
                AnnotationUtils.isCandidateClass(targetType, AbacPreAuthorize.class) &&
                !isSpringContainerClass(targetType)) {

            Map<Method, AbacPreAuthorize> annotatedMethods = null;
            try {
                annotatedMethods = MethodIntrospector.selectMethods(targetType,
                        (MethodIntrospector.MetadataLookup<AbacPreAuthorize>) method ->
                                AnnotatedElementUtils.findMergedAnnotation(method, AbacPreAuthorize.class));
            }
            catch (Throwable ex) {
                // An unresolvable type in a method signature, probably from a lazy bean - let's ignore it.
                if (log.isDebugEnabled()) {
                    log.debug("Could not resolve methods for bean with name '" + beanName + "'", ex);
                }
            }

            ConfigurableApplicationContext context = this.applicationContext;
            Assert.state(context != null, "No ApplicationContext set");

            for (Map.Entry<Method, AbacPreAuthorize> entry : annotatedMethods.entrySet()) {
//                AbacContextEntity  abacContextEntity = new AbacContextEntity();
//                AbacPreAuthorize abacPreAuthorize = entry.getValue();
//                Method method = entry.getKey();
//                Method methodToUse = AopUtils.selectInvocableMethod(method, context.getType(beanName));
//                String key = "pre:" + abacPreAuthorize.value();
//                ObjectReturn[] objectReturns = abacPreAuthorize.objectReturns();
//
//                // 设置返回对象
//                abacContextEntity.setResultObject(ObjectEntityParser.parse(methodToUse.getReturnType()));
//
//                // 设置object
//                List<AbacContextEntity.ObjectEntity> objectEntities = new ArrayList<>();
//                if (objectReturns != null) {
//                    for (ObjectReturn objectReturn : objectReturns) {
//                        AbacContextEntity.ObjectEntity objectEntity = ObjectEntityParser.parse(objectReturn.name(), objectReturn.returnType());
//                        objectEntities.add(objectEntity);
//                    }
//                }
//                abacContextEntity.setObjectEntities(objectEntities);
//
//                // 设置参数
//                List<AbacContextEntity.ObjectEntity> arguments = new ArrayList<>();
//                for (Parameter parameter : methodToUse.getParameters()) {
//                    arguments.add(ObjectEntityParser.parse(parameter.getName(), parameter.getType()));
//                }
//                abacContextEntity.setArguments(arguments);

                AbacContextJsonEntity  abacContextEntity = new AbacContextJsonEntity();
                AbacPreAuthorize abacPreAuthorize = entry.getValue();
                Method method = entry.getKey();
                Method methodToUse = AopUtils.selectInvocableMethod(method, context.getType(beanName));
                String key = "pre:" + abacPreAuthorize.value();
                ObjectReturn[] objectReturns = abacPreAuthorize.objectReturns();

                // 设置返回对象
                if (methodToUse.getReturnType() != Void.TYPE) {
                    abacContextEntity.setResultObject(jsonHelper.toJsonString(BeanHelper.instantiateBean(methodToUse.getReturnType())));
                }

                // 设置object
//                List<AbacContextEntity.ObjectEntity> objectEntities = new ArrayList<>();
//                if (objectReturns != null) {
//                    for (ObjectReturn objectReturn : objectReturns) {
//                        AbacContextEntity.ObjectEntity objectEntity = ObjectEntityParser.parse(objectReturn.name(), objectReturn.returnType());
//                        objectEntities.add(objectEntity);
//                    }
//                }
//                abacContextEntity.setObjectEntities(objectEntities);
                Map<String, String> objectEntities = new HashMap<>();
                if (objectReturns != null) {
                    for (ObjectReturn objectReturn : objectReturns) {
//                        AbacContextEntity.ObjectEntity objectEntity = ObjectEntityParser.parse(objectReturn.name(), objectReturn.returnType());
                        objectEntities.put(objectReturn.name(), jsonHelper.toJsonString(BeanHelper.instantiateBean(objectReturn.returnType())));
                    }
                }
                abacContextEntity.setObjectEntities(objectEntities);

                // 设置参数
//                List<AbacContextEntity.ObjectEntity> arguments = new ArrayList<>();
//                for (Parameter parameter : methodToUse.getParameters()) {
//                    arguments.add(ObjectEntityParser.parse(parameter.getName(), parameter.getType()));
//                }
//                abacContextEntity.setArguments(arguments);

                Map<String, String> arguments = new HashMap<>();
                for (Parameter parameter : methodToUse.getParameters()) {
                    arguments.put(parameter.getName(), jsonHelper.toJsonString(BeanHelper.instantiateBean(parameter.getType())));
                }
                abacContextEntity.setArguments(arguments);

                this.abacMappingHandlerMapping.register(key, abacContextEntity);
            }

            if (log.isDebugEnabled()) {
                log.debug(annotatedMethods.size() + " @ObjectReturn methods processed on bean '" +
                        beanName + "': " + annotatedMethods);
            }
        }
    }

    private void processBean(final String beanName, final Class<?> targetType) {
        if (!this.nonAnnotatedClasses.contains(targetType) &&
                AnnotationUtils.isCandidateClass(targetType, ObjectMethod.class) &&
                !isSpringContainerClass(targetType)) {

            Map<Method, ObjectMethod> annotatedMethods = null;
            try {
                annotatedMethods = MethodIntrospector.selectMethods(targetType,
                        (MethodIntrospector.MetadataLookup<ObjectMethod>) method ->
                                AnnotatedElementUtils.findMergedAnnotation(method, ObjectMethod.class));
            }
            catch (Throwable ex) {
                // An unresolvable type in a method signature, probably from a lazy bean - let's ignore it.
                if (log.isDebugEnabled()) {
                    log.debug("Could not resolve methods for bean with name '" + beanName + "'", ex);
                }
            }

            if (CollectionUtils.isEmpty(annotatedMethods)) {
                this.nonAnnotatedClasses.add(targetType);
                if (log.isTraceEnabled()) {
                    log.trace("No @EventListener annotations found on bean class: " + targetType.getName());
                }
            }
            else {
                // Non-empty set of methods
                ConfigurableApplicationContext context = this.applicationContext;
                Assert.state(context != null, "No ApplicationContext set");


                for (Map.Entry<Method, ObjectMethod> entry : annotatedMethods.entrySet()) {
                    ObjectMethod commandHandler = entry.getValue();
                    Method method = entry.getKey();
                    Method methodToUse = AopUtils.selectInvocableMethod(method, context.getType(beanName));

                    String key = commandHandler.value();
                    if (!StringUtils.hasText(key) || ".".equals(key)) {
                        key = methodToUse.getName();
                    }
                    AuthorizeObjectMethodAdapter authorizeObjectMethodAdapter = new AuthorizeObjectMethodAdapter(beanName, methodToUse);
                    authorizeObjectMethodAdapter.init(context);
                    this.manager.put(key, authorizeObjectMethodAdapter);
                }

                if (log.isDebugEnabled()) {
                    log.debug(annotatedMethods.size() + " @ObjectReturn methods processed on bean '" +
                            beanName + "': " + annotatedMethods);
                }
            }
        }
    }

    private static boolean isSpringContainerClass(Class<?> clazz) {
        return (clazz.getName().startsWith("org.springframework.") &&
                !AnnotatedElementUtils.isAnnotated(ClassUtils.getUserClass(clazz), Component.class));
    }


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Assert.isTrue(applicationContext instanceof ConfigurableApplicationContext,
                "ApplicationContext does not implement ConfigurableApplicationContext");
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }
}
