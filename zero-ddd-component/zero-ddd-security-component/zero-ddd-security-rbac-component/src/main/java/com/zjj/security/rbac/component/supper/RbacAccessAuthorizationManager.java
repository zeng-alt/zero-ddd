package com.zjj.security.rbac.component.supper;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.util.Assert;

import java.util.function.Supplier;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月09日 21:41
 */
public final class RbacAccessAuthorizationManager
		implements AuthorizationManager<RequestAuthorizationContext>, InitializingBean {

	private final RbacAccessService rbacAccessService;

	public RbacAccessAuthorizationManager(RbacAccessService rbacAccessService) {
		this.rbacAccessService = rbacAccessService;
	}

	@Override
	public AuthorizationDecision check(Supplier<Authentication> supplier, RequestAuthorizationContext object) {
		HttpServletRequest request = object.getRequest();
		Authentication authentication = supplier.get();

		return new AuthorizationDecision(
				rbacAccessService.verify(authentication.getPrincipal(), authentication.getAuthorities(), request));
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(rbacAccessService, "rbacAccessService must not be null");
	}

}
