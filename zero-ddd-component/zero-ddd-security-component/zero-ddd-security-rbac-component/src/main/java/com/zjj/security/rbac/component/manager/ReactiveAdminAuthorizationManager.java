package com.zjj.security.rbac.component.manager;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import reactor.core.publisher.Mono;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月25日 16:30
 */
public class ReactiveAdminAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext object) {
        return authentication.filter(this::isAdmin)
                .map(this::getAuthorizationDecision)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

    private AuthorizationDecision getAuthorizationDecision(Authentication authentication) {
        return new AuthorizationDecision(authentication.isAuthenticated());
    }

    private boolean isAdmin(Authentication authentication) {
        return "admin".equals(authentication.getName());
    }
}
