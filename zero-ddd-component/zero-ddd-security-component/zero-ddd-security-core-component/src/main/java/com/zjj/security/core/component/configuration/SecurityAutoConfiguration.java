package com.zjj.security.core.component.configuration;

import com.zjj.autoconfigure.component.security.WhiteListProperties;
import com.zjj.security.core.component.spi.WhiteListService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import java.util.List;

/**
 * @author zengJiaJun
 * @crateTime 2024年10月07日 19:09
 * @version 1.0
 */
@AutoConfiguration
@Import(JwtHelperAutoConfiguration.class)
@EnableConfigurationProperties({ WhiteListProperties.class })
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
	@ConditionalOnMissingBean
	public WhiteListService defaultWhiteListService(WhiteListProperties whiteListProperties) {
		return whiteListProperties::getIgnoreUrl;
	}


	@Bean
	@ConditionalOnMissingBean(AuthenticationEventPublisher.class)
	public AuthenticationEventPublisher defaultAuthenticationEventPublisher(
			ApplicationEventPublisher applicationEventPublisher) {
		return new DefaultAuthenticationEventPublisher(applicationEventPublisher);
	}

	@Bean
	@ConditionalOnMissingBean(AuthenticationManager.class)
	@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
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
