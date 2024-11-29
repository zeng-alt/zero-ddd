package com.zjj.security.core.component.configuration;

import com.zjj.autoconfigure.component.security.LoginSuccessHandler;
import com.zjj.autoconfigure.component.security.jwt.JwtCacheManage;
import com.zjj.autoconfigure.component.security.jwt.JwtHelper;
import com.zjj.security.core.component.configuration.properties.LoginProperties;
import com.zjj.security.core.component.supper.DefaultLoginFailureHandler;
import com.zjj.security.core.component.supper.DefaultLoginSuccessHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月30日 20:11
 */
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@EnableConfigurationProperties({ LoginProperties.class })
@ConditionalOnProperty(name = "security.login.enabled", havingValue = "true", matchIfMissing = true)
public class LoginHandlerAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public LoginSuccessHandler loginSuccessAuthenticationHandler(JwtCacheManage jwtCacheManage, JwtHelper jwtHelper) {
		return new DefaultLoginSuccessHandler(jwtCacheManage, jwtHelper);
	}

	@Bean
	@ConditionalOnMissingBean(AuthenticationFailureHandler.class)
	public AuthenticationFailureHandler loginFailureAuthenticationHandler() {
		return new DefaultLoginFailureHandler();
	}

}
