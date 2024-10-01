package com.zjj.security.core.component.configuration;

import com.zjj.autoconfigure.component.security.LoginSuccessHandler;
import com.zjj.autoconfigure.component.security.jwt.JwtCacheManage;
import com.zjj.autoconfigure.component.security.jwt.JwtHelper;
import com.zjj.autoconfigure.component.security.jwt.JwtProperties;
import com.zjj.security.core.component.supper.DefaultAccessDeniedHandler;
import com.zjj.security.core.component.supper.DefaultAuthenticationEntryPoint;
import com.zjj.security.core.component.supper.DefaultLoginFailureHandler;
import com.zjj.security.core.component.supper.DefaultLoginSuccessHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月30日 20:05
 */
@AutoConfiguration
public class AccessFailedAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new DefaultAuthenticationEntryPoint();
    }


    @Bean
    @ConditionalOnMissingBean
    public AccessDeniedHandler accessDeniedHandler() {
        return new DefaultAccessDeniedHandler();
    }

}
