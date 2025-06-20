package com.zjj.core.component;

import com.zjj.autoconfigure.component.json.JsonHelper;
import com.zjj.autoconfigure.component.tenant.MultiTenancyProperties;
import com.zjj.core.component.advice.GlobalServletExceptionAdvice;
import com.zjj.core.component.api.HttpEntityReturnMethodProcessor;
import com.zjj.core.component.crypto.EncryptionService;
import com.zjj.core.component.crypto.EncryptionServiceImpl;
import com.zjj.core.component.type.JsonConversionContext;
import com.zjj.core.component.type.JsonConversionStrategy;
import com.zjj.core.component.type.JsonConversionStrategyFactory;
import com.zjj.core.component.type.TypeConversionHelper;
import com.zjj.core.component.type.strategy.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.task.TaskDecorator;
import org.springframework.core.task.support.CompositeTaskDecorator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.HttpEntityMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月28日 21:56
 */
@AutoConfiguration
@AutoConfigurationPackage(basePackages = "com.zjj.core.component.type")
public class CoreAutoConfig {


    @Bean
    public ThreadPoolTaskExecutor bootstrapExecutor(ObjectProvider<TaskDecorator> taskDecorators) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);  // 设置核心线程池大小
        executor.setMaxPoolSize(50);   // 设置最大线程池大小
        executor.setQueueCapacity(100);  // 设置队列容量
        executor.setThreadNamePrefix("async-thread-");
        executor.setTaskDecorator(new CompositeTaskDecorator(taskDecorators.stream().toList()));
        executor.initialize();

        return executor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public BigDecimalTypeJsonConversionStrategy bigDecimalTypeJsonConversionStrategy() {
        return new BigDecimalTypeJsonConversionStrategy();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public BooleanTypeJsonConversionStrategy booleanTypeJsonConversionStrategy() {
        return new BooleanTypeJsonConversionStrategy();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public DoubleTypeJsonConversionStrategy doubleTypeJsonConversionStrategy() {
        return new DoubleTypeJsonConversionStrategy();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public IntegerTypeJsonConversionStrategy integerTypeJsonConversionStrategy() {
        return new IntegerTypeJsonConversionStrategy();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LocalDateTimeTypeJsonConversionStrategy localDateTimeTypeJsonConversionStrategy() {
        return new LocalDateTimeTypeJsonConversionStrategy();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LocalTimeTypeJsonConversionStrategy localTimeTypeJsonConversionStrategy() {
        return new LocalTimeTypeJsonConversionStrategy();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LocalDateTypeJsonConversionStrategy localDateTypeJsonConversionStrategy() {
        return new LocalDateTypeJsonConversionStrategy();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public StringTypeJsonConversionStrategy stringTypeJsonConversionStrategy() {
        return new StringTypeJsonConversionStrategy();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public ListTypeJsonConversionStrategy listTypeJsonConversionStrategy(JsonHelper jsonHelper) {
        return new ListTypeJsonConversionStrategy(jsonHelper);
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public TypeConversionHelper typeConversionHelper() {
        return new TypeConversionHelper();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public JsonConversionStrategyFactory jsonConversionStrategyFactory(ObjectProvider<JsonConversionStrategy> objectProvider) {
        return new JsonConversionStrategyFactory(objectProvider);
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public JsonConversionContext jsonConversionContext(JsonConversionStrategyFactory jsonConversionStrategyFactory) {
        return new JsonConversionContext(jsonConversionStrategyFactory);
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(WebMvcConfigurer.class)
    @ConditionalOnBean(DelegatingWebMvcConfiguration.class)
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    @RequiredArgsConstructor
    static class ReturnValueHandler {

        private final RequestMappingHandlerAdapter requestMappingHandlerAdapter;
        private final MessageSourceAccessor messageSourceAccessor;
        @PostConstruct
        public void post() {
            synchronized (RequestMappingHandlerAdapter.class) {
                List<HandlerMethodReturnValueHandler> returnValueHandlers = requestMappingHandlerAdapter.getReturnValueHandlers();
                ArrayList<HandlerMethodReturnValueHandler> handlerMethodReturnValueHandlers = new ArrayList<>();

                HttpEntityReturnMethodProcessor customHttpEntityMethodProcessor = null;
                RequestResponseBodyMethodProcessor processor = null;
                for (HandlerMethodReturnValueHandler handlerMethodReturnValueHandler : returnValueHandlers) {
                    if (handlerMethodReturnValueHandler instanceof HttpEntityMethodProcessor) {
                        customHttpEntityMethodProcessor = new HttpEntityReturnMethodProcessor(requestMappingHandlerAdapter.getMessageConverters(), requestMappingHandlerAdapter.getErrorResponseInterceptors(), messageSourceAccessor);
                        handlerMethodReturnValueHandlers.add(customHttpEntityMethodProcessor);
                    }
                    if (handlerMethodReturnValueHandler instanceof RequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor) {
                        processor = requestResponseBodyMethodProcessor;
                    }

                    handlerMethodReturnValueHandlers.add(handlerMethodReturnValueHandler);
                }

                if (customHttpEntityMethodProcessor != null) {
                    customHttpEntityMethodProcessor.setRequestResponseBodyMethodProcessor(processor);
                }

                requestMappingHandlerAdapter.setReturnValueHandlers(handlerMethodReturnValueHandlers);
            }
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(NoResourceFoundException.class)
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    static class ServletAdviceConfig {
        @Bean
        @Order(Ordered.HIGHEST_PRECEDENCE)
        public GlobalServletExceptionAdvice globalServletExceptionAdvice() {
            return new GlobalServletExceptionAdvice();
        }
    }

    @Bean
    @ConditionalOnMissingBean
    public EncryptionService encryptionService(ObjectProvider<MultiTenancyProperties>  properties) {
        MultiTenancyProperties ifAvailable = properties.getIfAvailable();
        if (ifAvailable != null && ifAvailable.getKey() != null) {
            return new EncryptionServiceImpl(ifAvailable.getKey());
        }
        return new EncryptionServiceImpl(UUID.randomUUID().toString());
    }
}
