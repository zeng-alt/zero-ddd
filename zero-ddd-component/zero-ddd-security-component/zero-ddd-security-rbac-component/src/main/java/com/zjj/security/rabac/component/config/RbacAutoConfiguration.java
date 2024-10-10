package com.zjj.security.rabac.component.config;

import com.zjj.autoconfigure.component.security.SecurityBuilderCustomizer;
import com.zjj.security.rabac.component.domain.HttpResource;
import com.zjj.security.rabac.component.supper.*;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.Collection;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月09日 20:44
 */
@AutoConfiguration
public class RbacAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public HttpResourceService httpResourceService() {
		return new DefaultHttpResourceService();
	}

	@Bean
	@ConditionalOnMissingBean
	public RbacAccessService rbacAccessService(HttpResourceService httpResourceService) {
		return new DefaultRbacAccessService(httpResourceService);
	}

	@Bean
	public AuthorizationManager<RequestAuthorizationContext> rbacAuthorizationManager(
			RbacAccessService rbacAccessService) {
		return new RbacAccessAuthorizationManager(rbacAccessService);
	}

	@Bean
	public SecurityBuilderCustomizer rbacCustomizer(
			AuthorizationManager<RequestAuthorizationContext> rbacAuthorizationManager) {
		return http -> http.authorizeHttpRequests(a -> a.anyRequest().access(rbacAuthorizationManager));
	}

}
