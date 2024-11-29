package com.zjj.security.rbac.component.config;

import com.zjj.security.rbac.component.supper.DefaultRbacAccessService;
import com.zjj.security.rbac.component.supper.HttpResourceService;
import com.zjj.security.rbac.component.supper.RbacAccessAuthorizationManager;
import com.zjj.security.rbac.component.supper.RbacAccessService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月09日 20:44
 */
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class RbacWebAutoConfiguration {

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

//	@Bean
//	public SecurityBuilderCustomizer rbacCustomizer(
//			AuthorizationManager<RequestAuthorizationContext> rbacAuthorizationManager) {
//		return http -> http.authorizeHttpRequests(a -> a.anyRequest().access(rbacAuthorizationManager));
//	}

}
