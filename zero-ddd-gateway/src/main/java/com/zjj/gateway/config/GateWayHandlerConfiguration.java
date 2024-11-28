package com.zjj.gateway.config;

import com.zjj.autoconfigure.component.json.JsonHelper;
import com.zjj.gateway.advice.GateWayExceptionAdvice;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

import java.util.Collections;
import java.util.List;


/**
 * 覆盖默认的异常处理
 */
@Configuration
@EnableConfigurationProperties({ServerProperties.class, WebProperties.class})
public class GateWayHandlerConfiguration {

    private final ServerProperties serverProperties;

    private final ApplicationContext applicationContext;

    private final WebProperties.Resources resourceProperties;

    private final List<ViewResolver> viewResolvers;

    private final ServerCodecConfigurer serverCodecConfigurer;

    public GateWayHandlerConfiguration(ServerProperties serverProperties,
                                       WebProperties resourceProperties,
                                       ObjectProvider<List<ViewResolver>> viewResolversProvider,
                                       ServerCodecConfigurer serverCodecConfigurer,
                                       ApplicationContext applicationContext) {
        this.serverProperties = serverProperties;
        this.applicationContext = applicationContext;
        this.resourceProperties = resourceProperties.getResources();
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GateWayExceptionAdvice errorWebExceptionHandler(ErrorAttributes errorAttributes, JsonHelper jsonHelper) {
        GateWayExceptionAdvice exceptionHandler = new GateWayExceptionAdvice(
                errorAttributes, 
                this.resourceProperties,
                this.serverProperties.getError(), 
                this.applicationContext,
                jsonHelper
        );
        exceptionHandler.setViewResolvers(this.viewResolvers);
        exceptionHandler.setMessageWriters(this.serverCodecConfigurer.getWriters());
        exceptionHandler.setMessageReaders(this.serverCodecConfigurer.getReaders());
        return exceptionHandler;
    }
    
}