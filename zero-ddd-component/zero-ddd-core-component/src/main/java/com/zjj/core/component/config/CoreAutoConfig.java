package com.zjj.core.component.config;

import com.zjj.core.component.advice.GlobalServletExceptionAdvice;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月28日 21:56
 */
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class CoreAutoConfig {


//    @Bean(bootstrap = BACKGROUND)
//    @ConditionalOnClass(ResponseBodyAdvice.class)
//    public GlobalResultAdvice globalResultAdvice(ObjectMapper objectMapper, ObjectProvider<ResponseAdviceProvider> responseAdviceProvider) {
//        return new GlobalResultAdvice(objectMapper, responseAdviceProvider);
//    }

    @Configuration
    @ConditionalOnClass(NoResourceFoundException.class)
    static class ServletAdviceConfig {
        @Bean
        @Order(Ordered.HIGHEST_PRECEDENCE)
        public GlobalServletExceptionAdvice globalServletExceptionAdvice() {
            return new GlobalServletExceptionAdvice();
        }
    }
}
