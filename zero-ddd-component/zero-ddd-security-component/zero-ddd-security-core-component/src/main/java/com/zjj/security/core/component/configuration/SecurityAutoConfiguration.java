package com.zjj.security.core.component.configuration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;

/**
 * @author zengJiaJun
 * @crateTime 2024年10月07日 19:09
 * @version 1.0
 */
@EnableWebSecurity
@AutoConfiguration
public class SecurityAutoConfiguration {

	// @Bean
	// @ConditionalOnMissingBean
	// public JacksonSerializer<Map<String, ?>> jacksonSerializer(ObjectMapper
	// objectMapper) {
	// return new JacksonSerializer<>(objectMapper);
	// }
	//
	// @Bean
	// @ConditionalOnMissingBean
	// public JacksonDeserializer<Map<String, ?>> jacksonDeserializer(ObjectMapper
	// objectMapper) {
	// return new JacksonDeserializer<>(objectMapper);
	// }

	@Bean
	@ConditionalOnMissingBean(AuthenticationEventPublisher.class)
	public AuthenticationEventPublisher defaultAuthenticationEventPublisher(
			ApplicationEventPublisher applicationEventPublisher) {
		return new DefaultAuthenticationEventPublisher(applicationEventPublisher);
	}

	@Bean
	@ConditionalOnMissingBean(AuthenticationManager.class)
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration,
			List<AuthenticationProvider> authenticationProviders,
			AuthenticationEventPublisher authenticationEventPublisher

	) throws Exception {
		AuthenticationManager authenticationManager = configuration.getAuthenticationManager();
		ProviderManager providerManager = new ProviderManager(authenticationProviders, authenticationManager);
		providerManager.setAuthenticationEventPublisher(authenticationEventPublisher);
		return providerManager;
	}

}
