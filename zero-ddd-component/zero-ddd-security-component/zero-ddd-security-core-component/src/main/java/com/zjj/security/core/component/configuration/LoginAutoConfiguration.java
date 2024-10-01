package com.zjj.security.core.component.configuration;

import com.zjj.autoconfigure.component.security.SecurityBuilderCustomizer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月29日 20:01
 */
@AutoConfiguration
@EnableConfigurationProperties(UsernameLoginProperties.class)
public class LoginAutoConfiguration {

    @Bean
    @ConditionalOnProperty(name = "security.username-login.enabled", havingValue = "true", matchIfMissing = true)
    public SecurityBuilderCustomizer initiateLoginCustomizer(
            UsernameLoginProperties usernameLoginProperties,
            AuthenticationSuccessHandler loginSuccessAuthenticationHandler,
            AuthenticationFailureHandler loginFailureAuthenticationHandler
    ) {
        return http -> http
                .formLogin(
                    formLogin -> formLogin
                        .loginProcessingUrl(usernameLoginProperties.getLoginPath())
                        .usernameParameter(usernameLoginProperties.getUsernameParameter())
                        .passwordParameter(usernameLoginProperties.getPasswordParameter())
                        .failureHandler(loginFailureAuthenticationHandler)
                        .successHandler(loginSuccessAuthenticationHandler)
                );
    }

    @Bean
    @ConditionalOnProperty(name = "security.username-login.enabled", havingValue = "false")
    public SecurityBuilderCustomizer shutDownLoginCustomizer() {
        return http -> http.formLogin(AbstractHttpConfigurer::disable);
    }
}
