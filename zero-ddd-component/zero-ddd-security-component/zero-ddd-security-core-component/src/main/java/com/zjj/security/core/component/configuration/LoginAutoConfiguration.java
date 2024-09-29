package com.zjj.security.core.component.configuration;

import com.zjj.autoconfigure.component.security.SecurityBuilderCustomizer;
import com.zjj.security.core.component.supper.DefaultLoginFailureHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月29日 16:01
 */
@Configuration
@EnableConfigurationProperties(UsernameLoginProperties.class)
public class LoginAutoConfiguration {

    @Bean
    @ConditionalOnProperty(name = "security.username-login.enabled", havingValue = "true", matchIfMissing = true)
    public SecurityBuilderCustomizer initiateLoginCustomizer(
            UsernameLoginProperties usernameLoginProperties,
            AuthenticationSuccessHandler loginSuccessHandler,
            AuthenticationFailureHandler loginFailureHandler
    ) {
        return http -> http
                .formLogin(
                    formLogin -> formLogin
                        .loginProcessingUrl(usernameLoginProperties.getLoginPath())
                        .usernameParameter(usernameLoginProperties.getUsernameParameter())
                        .passwordParameter(usernameLoginProperties.getPasswordParameter())
                        .failureHandler(loginFailureHandler)
                        .successHandler(loginSuccessHandler)
                );
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationFailureHandler.class)
    public AuthenticationFailureHandler loginFailureAuthenticationHandler() {
        return new DefaultLoginFailureHandler();
    }

    @Bean
    @ConditionalOnMissingClass(value = "com.zjj.security.jwt.component.supper.DefaultLoginSuccessHandler")
    public AuthenticationSuccessHandler loginSuccessAuthenticationHandler() {
        return new SavedRequestAwareAuthenticationSuccessHandler();
    }

    @Bean
    @ConditionalOnProperty(name = "security.username-login.enabled", havingValue = "false")
    public SecurityBuilderCustomizer shutDownLoginCustomizer() {
        return http -> http.formLogin(AbstractHttpConfigurer::disable);
    }
}
