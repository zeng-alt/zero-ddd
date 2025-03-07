package com.zjj.example.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.HttpEntityMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig {

    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @PostConstruct
    public void post() {
        synchronized (RequestMappingHandlerAdapter.class) {
            List<HandlerMethodReturnValueHandler> returnValueHandlers = requestMappingHandlerAdapter.getReturnValueHandlers();
            ArrayList<HandlerMethodReturnValueHandler> handlerMethodReturnValueHandlers = new ArrayList<>();

            CustomHttpEntityMethodProcessor customHttpEntityMethodProcessor = null;
            RequestResponseBodyMethodProcessor processor = null;
            for (HandlerMethodReturnValueHandler handlerMethodReturnValueHandler : returnValueHandlers) {
                if (handlerMethodReturnValueHandler instanceof HttpEntityMethodProcessor) {
                    customHttpEntityMethodProcessor = new CustomHttpEntityMethodProcessor(requestMappingHandlerAdapter.getMessageConverters(), requestMappingHandlerAdapter.getErrorResponseInterceptors(), messageSourceAccessor);
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

//    @Override
//    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
//        // Register custom return value handlers, including HttpEntityMethodProcessor
//        handlers.removeIf(handler -> handler instanceof HttpEntityMethodProcessor);
//
//        // Add your custom handler
//        handlers.add(0, new CustomHttpEntityMethodProcessor());
////        handlers.add(new HttpEntityMethodProcessor(messageConverters()));
//    }

    private List<HttpMessageConverter<?>> messageConverters() {
        // Return your list of message converters, for example:
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(new MappingJackson2HttpMessageConverter());
        return converters;
    }
}
