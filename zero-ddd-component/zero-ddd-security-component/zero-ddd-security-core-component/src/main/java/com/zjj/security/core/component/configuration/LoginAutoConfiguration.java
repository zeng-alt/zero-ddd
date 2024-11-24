package com.zjj.security.core.component.configuration;

import com.zjj.autoconfigure.component.security.SecurityBuilderCustomizer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月29日 20:01
 */
@AutoConfiguration
@EnableConfigurationProperties(UsernameLoginProperties.class)
public class LoginAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(name = "security.username-login.enabled", havingValue = "true")
	public UserDetailsService inMemoryUserDetailsManager(PasswordEncoder passwordEncoder) {

		return new InMemoryUserDetailsManager(
				List.of(User.withUsername("root").password(passwordEncoder.encode("123456")).roles("ADMIN").build()));
	}

	@Bean
	@ConditionalOnMissingBean(PasswordEncoder.class)
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(name = "security.username-login.enabled", havingValue = "true")
	public DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService) {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		return daoAuthenticationProvider;
	}

	@Bean
	@ConditionalOnProperty(name = "security.username-login.enabled", havingValue = "true")
	public SecurityBuilderCustomizer initiateLoginCustomizer(UsernameLoginProperties usernameLoginProperties,
			AuthenticationSuccessHandler loginSuccessAuthenticationHandler,
			AuthenticationFailureHandler loginFailureAuthenticationHandler) {
		return http -> http.formLogin(formLogin -> formLogin.loginProcessingUrl(usernameLoginProperties.getLoginPath())
				.usernameParameter(usernameLoginProperties.getUsernameParameter())
				.passwordParameter(usernameLoginProperties.getPasswordParameter())
				.failureHandler(loginFailureAuthenticationHandler).successHandler(loginSuccessAuthenticationHandler));
	}

	@Bean
	@ConditionalOnProperty(name = "security.username-login.enabled", havingValue = "false", matchIfMissing = true)
	public SecurityBuilderCustomizer shutDownLoginCustomizer() {
		return http -> http.formLogin(AbstractHttpConfigurer::disable).logout(AbstractHttpConfigurer::disable);
	}

}
