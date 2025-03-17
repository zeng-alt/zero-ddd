package com.zjj.core.component.config;

import com.zjj.core.component.advice.GlobalServletExceptionAdvice;
import com.zjj.core.component.api.HttpEntityReturnMethodProcessor;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.HttpEntityMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月28日 21:56
 */
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class CoreAutoConfig {


    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(WebMvcConfigurer.class)
    @ConditionalOnBean(DelegatingWebMvcConfiguration.class)
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
    static class ServletAdviceConfig {
        @Bean
        @Order(Ordered.HIGHEST_PRECEDENCE)
        public GlobalServletExceptionAdvice globalServletExceptionAdvice() {
            return new GlobalServletExceptionAdvice();
        }
    }
}
