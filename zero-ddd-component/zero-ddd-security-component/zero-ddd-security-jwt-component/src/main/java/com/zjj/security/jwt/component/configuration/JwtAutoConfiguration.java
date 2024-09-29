package com.zjj.security.jwt.component.configuration;

import com.zjj.autoconfigure.component.security.LoginSuccessHandler;
import com.zjj.autoconfigure.component.security.SecurityBuilderCustomizer;
import com.zjj.autoconfigure.component.security.jwt.JwtCacheManage;
import com.zjj.autoconfigure.component.security.jwt.JwtHelper;
import com.zjj.security.jwt.component.JwtAuthenticationTokenFilter;
import com.zjj.security.jwt.component.JwtRenewFilter;
import com.zjj.security.jwt.component.supper.*;
import jakarta.servlet.Filter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ResolvableType;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月26日 20:21
 */
@Configuration
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@EnableConfigurationProperties(JwtProperties.class)
public class JwtAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public JwtHelper jwtHelper(JwtProperties jwtProperties) {
        return new DefaultJwtHelper(jwtProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtCacheManage jwtCacheManage(JwtProperties jwtProperties) {
        return new DefaultJwtCacheManage(jwtProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(JwtHelper jwtHelper, JwtCacheManage jwtCacheManage) {
        return new DefaultJwtAuthenticationTokenFilter(jwtHelper, jwtCacheManage);
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtRenewFilter jwtRenewFilter(JwtCacheManage jwtCacheManage) {
        return new DefaultJwtRenewFilter(jwtCacheManage);
    }

    @Bean
    @ConditionalOnMissingBean
    public LoginSuccessHandler loginSuccessHandler(JwtCacheManage jwtCacheManage, JwtHelper jwtHelper) {
        return new DefaultLoginSuccessHandler(jwtCacheManage, jwtHelper);
    }

    @Bean
    @ConditionalOnProperty(name = "security.jwt.enabled", havingValue = "true", matchIfMissing = true)
    public SecurityBuilderCustomizer jwtCustomizer(JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter, JwtRenewFilter jwtRenewFilter) {
        return http -> {
            Class type = ResolvableType.forType(jwtAuthenticationTokenFilter.getClass()).getRawClass();
            http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
            http.addFilterAfter(jwtRenewFilter, type);
        };
    }
}
