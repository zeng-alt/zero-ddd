package com.zjj.security.jwt.component.configuration;

import com.zjj.autoconfigure.component.security.ServerHttpSecurityBuilderCustomizer;
import com.zjj.autoconfigure.component.security.jwt.JwtCacheManage;
import com.zjj.autoconfigure.component.security.jwt.JwtHelper;
import com.zjj.autoconfigure.component.security.jwt.JwtProperties;
import com.zjj.security.jwt.component.JwtReactiveAuthenticationTokenFilter;
import com.zjj.security.jwt.component.supper.DefaultJwtReactiveAuthenticationTokenFilter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月26日 21:11
 */
@Configuration
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@ConditionalOnProperty(name = "security.jwt.enabled", havingValue = "true", matchIfMissing = true)
public class JwtReactiveAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public JwtReactiveAuthenticationTokenFilter jwtReactiveAuthenticationTokenFilter(JwtHelper jwtHelper,
                                                                                     JwtCacheManage jwtCacheManage,
                                                                                     JwtProperties jwtProperties) {
        return new DefaultJwtReactiveAuthenticationTokenFilter(jwtHelper, jwtCacheManage, jwtProperties);
    }

    @Order(9)
    @Bean
    public ServerHttpSecurityBuilderCustomizer jwtCustomizer(JwtReactiveAuthenticationTokenFilter jwtReactiveAuthenticationTokenFilter) {
        return http -> http
                .addFilterAt(jwtReactiveAuthenticationTokenFilter, SecurityWebFiltersOrder.HTTP_BASIC);
    }
}