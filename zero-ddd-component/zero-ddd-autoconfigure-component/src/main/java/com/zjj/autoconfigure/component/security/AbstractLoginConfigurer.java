package com.zjj.autoconfigure.component.security;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author zengJiaJun
 * @crateTime 2024年10月07日 16:40
 * @version 1.0
 */
public abstract class AbstractLoginConfigurer<T extends AbstractHttpConfigurer<T, B>, B extends HttpSecurityBuilder<B>, F extends AbstractAuthenticationProcessingFilter>
		extends AbstractHttpConfigurer<T, B> {

	private F authFilter;

	protected AbstractLoginConfigurer(F authFilter) {
		this.authFilter = authFilter;
	}

	public AbstractLoginConfigurer<T, B, F> successHandler(AuthenticationSuccessHandler authenticationSuccessHandler) {
		authFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
		return this;
	}

	public AbstractLoginConfigurer<T, B, F> failureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
		authFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
		return this;
	}

	public AbstractLoginConfigurer<T, B, F> eventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		authFilter.setApplicationEventPublisher(applicationEventPublisher);
		return this;
	}

	public AbstractLoginConfigurer<T, B, F> authenticationManager(AuthenticationManager authenticationManager) {
		authFilter.setAuthenticationManager(authenticationManager);
		return this;
	}

	protected final F getAuthenticationFilter() {
		return this.authFilter;
	}

}
