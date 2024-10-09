package com.zjj.security.core.component.configuration;

import com.zjj.autoconfigure.component.security.AbstractLoginConfigurer;
import com.zjj.autoconfigure.component.security.SecurityBuilderCustomizer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.IpAddressAuthorizationManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月29日 21:04
 */
@AutoConfiguration
@Import({ LoginAutoConfiguration.class, JwtHelperAutoConfiguration.class, LoginHandlerAutoConfiguration.class,
		AccessFailedAutoConfiguration.class })
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class WebSecurityAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(SecurityFilterChain.class)
	public SecurityFilterChain filterChain(
			HttpSecurity http, ObjectProvider<SecurityBuilderCustomizer> customizers,
			ObjectProvider<AbstractLoginConfigurer> configurers,
			AuthenticationSuccessHandler loginSuccessAuthenticationHandler,
			AuthenticationFailureHandler loginFailureAuthenticationHandler, AuthenticationManager authenticationManager,
			AuthenticationEntryPoint authenticationEntryPoint, AccessDeniedHandler accessDeniedHandler,
			ApplicationEventPublisher applicationEventPublisher

	) throws Exception {
//		UserDetails
		HttpSecurity httpSecurity = http.csrf(AbstractHttpConfigurer::disable)
				.httpBasic(AbstractHttpConfigurer::disable).cors(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(author -> author.requestMatchers(HttpMethod.POST, "/login/**").permitAll()
						.requestMatchers("/h2-console/**").permitAll().requestMatchers("/graphiql/**").permitAll()
						.requestMatchers("/graphql/**").permitAll()
				)
				.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
				.exceptionHandling(e -> e
						.authenticationEntryPoint(authenticationEntryPoint)
						.accessDeniedHandler(accessDeniedHandler))
				.formLogin(Customizer.withDefaults());
		// .formLogin(
		// AbstractHttpConfigurer::disable // 禁用，前后端分离项目
		// .loginPage("/login")
		// .failureHandler(loginAuthenticationHandler)
		// .successHandler(loginAuthenticationHandler)
		// .permitAll()
		// .loginPage("/login/mobilecode")
		// .failureHandler(loginAuthenticationHandler)
		// .successHandler(loginAuthenticationHandler)
		// .permitAll()
		// );

		customizers.orderedStream().forEach(customizer -> {
			try {
				customizer.customize(httpSecurity);
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		});

		configurers.orderedStream().forEach(configurer -> {
			try {
				http.with(configurer, c -> c.successHandler(loginSuccessAuthenticationHandler)
						.failureHandler(loginFailureAuthenticationHandler).authenticationManager(authenticationManager)
						.eventPublisher(applicationEventPublisher));
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		});

		return http.build();
	}

}
