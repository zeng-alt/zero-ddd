package com.zjj.domain.component.command;

import lombok.extern.slf4j.Slf4j;
import org.jmolecules.architecture.cqrs.CommandHandler;
import org.springframework.aop.framework.autoproxy.AutoProxyUtils;
import org.springframework.aop.scope.ScopedObject;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ApplicationListenerMethodAdapter;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListenerFactory;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zengJiaJun
 * @crateTime 2025年02月17日 21:30
 * @version 1.0
 */
@Slf4j
public class CommandHandlerMethodProcessor implements SmartInitializingSingleton, BeanFactoryPostProcessor, ApplicationContextAware {

    @Nullable
    private volatile TransactionalEventListenerFactory factory;
    @Nullable
    private ConfigurableListableBeanFactory beanFactory;
    @Nullable
    private ConfigurableApplicationContext applicationContext;
    @Nullable
    private CommandDispatcher commandDispatcher;
    private final Set<Class<?>> nonAnnotatedClasses = ConcurrentHashMap.newKeySet(64);
//    final Map<String, ApplicationListener<?>> retrieverCache = new ConcurrentHashMap<>(64);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        Assert.isTrue(applicationContext instanceof ConfigurableApplicationContext,
                "ApplicationContext does not implement ConfigurableApplicationContext");
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }

    @Override
    public void afterSingletonsInstantiated() {
        if (this.factory == null) {
            this.factory = new CommandTransactionalEventListenerFactory(this.applicationContext.getBean(TransactionTemplate.class));
        }
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
                        processBean(beanName, type);
                    }
                    catch (Throwable ex) {
                        throw new BeanInitializationException("Failed to process @EventListener " +
                                "annotation on bean with name '" + beanName + "': " + ex.getMessage(), ex);
                    }
                }
            }
        }
    }

    private void processBean(final String beanName, final Class<?> targetType) {
        if (!this.nonAnnotatedClasses.contains(targetType) &&
                AnnotationUtils.isCandidateClass(targetType, CommandHandler.class) &&
                !isSpringContainerClass(targetType)) {

            Map<Method, CommandHandler> annotatedMethods = null;
            try {
                annotatedMethods = MethodIntrospector.selectMethods(targetType,
                        (MethodIntrospector.MetadataLookup<CommandHandler>) method ->
                                AnnotatedElementUtils.findMergedAnnotation(method, CommandHandler.class));
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


                for (Map.Entry<Method, CommandHandler> entry : annotatedMethods.entrySet()) {
                    CommandHandler commandHandler = entry.getValue();
                    Method method = entry.getKey();
                    Method methodToUse = AopUtils.selectInvocableMethod(method, context.getType(beanName));
                    ApplicationListener<?> applicationListener =
                            this.factory.createApplicationListener(beanName, targetType, methodToUse);

                    String key = commandHandler.namespace() + "." + commandHandler.name();
                    if (!StringUtils.hasText(key) || ".".equals(key)) {
                        key = methodToUse.getParameterTypes()[0].getName();
                    }
                    if (applicationListener instanceof CommandTransactionalApplicationListenerMethodAdapter alma) {
                        alma.init(context);
                    }
                    this.commandDispatcher.addApplicationListener(key, applicationListener);
                }

                if (log.isDebugEnabled()) {
                    log.debug(annotatedMethods.size() + " @EventListener methods processed on bean '" +
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
        ObjectProvider<CommandDispatcher> beanProvider = beanFactory.getBeanProvider(CommandDispatcher.class);
        this.commandDispatcher = beanProvider.getIfAvailable();
        Assert.notNull(this.commandDispatcher, "CommandDispatcher is NULL");
    }
}
