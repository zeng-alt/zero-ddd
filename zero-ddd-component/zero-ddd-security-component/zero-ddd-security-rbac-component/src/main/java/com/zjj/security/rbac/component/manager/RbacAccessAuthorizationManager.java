package com.zjj.security.rbac.component.manager;

import com.zjj.security.rbac.component.handler.ResourceHandler;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public final class RbacAccessAuthorizationManager
		implements AuthorizationManager<RequestAuthorizationContext>, InitializingBean {

	private final ParseManager parseManager;

	@Override
	public AuthorizationDecision check(Supplier<Authentication> supplier, RequestAuthorizationContext object) {
		Authentication authentication = supplier.get();
		if (authentication == null) return new AuthorizationDecision(false);
		ResourceHandler handler = parseManager.parse(object.getRequest());
		return new AuthorizationDecision(handler.handler(authentication, object));
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(parseManager, "parseManager must not be null");
	}

}
