package com.zjj.security.fast.auth.component.configuration;

import com.zjj.autoconfigure.component.security.ServerHttpSecurityBuilderCustomizer;
import com.zjj.autoconfigure.component.security.jwt.JwtCacheManage;
import com.zjj.autoconfigure.component.security.jwt.JwtProperties;
import com.zjj.security.fast.auth.component.filter.ReactiveFastAuthenticationFilter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;

import static org.springframework.security.config.web.server.SecurityWebFiltersOrder.HTTP_BASIC;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月28日 21:07
 */
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class ReactiveFastAuthAutoConfiguration {

    @Bean
    public ReactiveFastAuthenticationFilter fastAuthenticationFilter(JwtCacheManage jwtCacheManage, JwtProperties jwtProperties) {
        return new ReactiveFastAuthenticationFilter(jwtCacheManage, jwtProperties);
    }


    @Bean
    public ServerHttpSecurityBuilderCustomizer fastAuthCustomizer(ReactiveFastAuthenticationFilter fastAuthenticationFilter) {
        return http -> http.addFilterBefore(fastAuthenticationFilter, HTTP_BASIC);
    }
}
