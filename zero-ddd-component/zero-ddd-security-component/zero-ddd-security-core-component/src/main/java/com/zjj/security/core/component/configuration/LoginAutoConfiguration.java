package com.zjj.security.core.component.configuration;

import com.zjj.autoconfigure.component.security.SecurityBuilderCustomizer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月29日 20:01
 */
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@EnableConfigurationProperties(UsernameLoginProperties.class)
public class LoginAutoConfiguration {


	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(name = "security.username-login.enabled", havingValue = "true")
	public DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		return daoAuthenticationProvider;
	}

//	@Bean
//	public Jackson2ObjectMapperBuilderCustomizer userDeserializerCustomizer() {
//		return new UserDeserializerCustomizer();
//	}

//	@Bean
//	public Module userModule() {
//		return new SimpleModule().addDeserializer(User.class, UserDeserializerCustomizer.UserDeserializer.INSTANCE);
//	}


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
