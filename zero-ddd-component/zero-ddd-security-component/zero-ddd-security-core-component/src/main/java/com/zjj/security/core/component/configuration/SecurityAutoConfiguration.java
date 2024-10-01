package com.zjj.security.core.component.configuration;

import com.zjj.autoconfigure.component.security.SecurityBuilderCustomizer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月29日 21:04
 */
@AutoConfiguration
@Import({LoginAutoConfiguration.class, JwtHelperAutoConfiguration.class, LoginHandlerAutoConfiguration.class, AccessFailedAutoConfiguration.class})
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class SecurityAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(SecurityFilterChain.class)
    public SecurityFilterChain filterChain(HttpSecurity http, List<SecurityBuilderCustomizer> customizers) throws Exception {

        HttpSecurity httpSecurity = http
                .authorizeHttpRequests(
                        author ->
                                author
                                        .requestMatchers(HttpMethod.POST, "/login/**").permitAll()
                                        .requestMatchers("/h2-console/**").permitAll()
                                        .requestMatchers("/graphiql/**").permitAll()
                                        .requestMatchers("/graphql/**").permitAll()
                                        .anyRequest().authenticated()
                )
                .headers(
                        headers -> headers
                                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                ).formLogin(Customizer.withDefaults());
//                .formLogin(
//                        AbstractHttpConfigurer::disable // 禁用，前后端分离项目
//                         .loginPage("/login")
//                        .failureHandler(loginAuthenticationHandler)
//                        .successHandler(loginAuthenticationHandler)
//                        .permitAll()
//                        .loginPage("/login/mobilecode")
//                        .failureHandler(loginAuthenticationHandler)
//                        .successHandler(loginAuthenticationHandler)
//                        .permitAll()
//                );

        customizers.forEach(customizer -> {
            try {
                customizer.customize(httpSecurity);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
//        http.apply()
        return http.build();


    }
}
