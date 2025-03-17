package com.zjj.excel.component.configuration;

import com.zjj.excel.component.processor.mvc.HttpExcelMethodProcessor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月17日 11:34
 */
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ExcelImportAutoConfiguration {

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(WebMvcConfigurer.class)
//    @ConditionalOnBean(DelegatingWebMvcConfiguration.class)
    static class resolveArgumentHandler implements WebMvcConfigurer {

        private final List<HttpMessageConverter<?>> converters;

        public resolveArgumentHandler(List<HttpMessageConverter<?>> converters) {
            this.converters = converters;
        }

        @Override
        public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
            resolvers.add(new HttpExcelMethodProcessor(converters));
        }
    }
}
