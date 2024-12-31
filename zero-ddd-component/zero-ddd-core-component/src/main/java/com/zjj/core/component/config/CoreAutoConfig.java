package com.zjj.core.component.config;

import com.zjj.autoconfigure.component.core.ResponseAdviceProvider;
import com.zjj.autoconfigure.component.json.JsonHelper;
import com.zjj.core.component.advice.GlobalResultAdvice;
import com.zjj.core.component.advice.GlobalServletExceptionAdvice;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月28日 21:56
 */
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class CoreAutoConfig {


    @Bean
    public GlobalResultAdvice globalResultAdvice(JsonHelper jsonHelper, ObjectProvider<ResponseAdviceProvider> responseAdviceProvider) {
        return new GlobalResultAdvice(jsonHelper, responseAdviceProvider);
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GlobalServletExceptionAdvice globalServletExceptionAdvice() {
        return new GlobalServletExceptionAdvice();
    }
}
