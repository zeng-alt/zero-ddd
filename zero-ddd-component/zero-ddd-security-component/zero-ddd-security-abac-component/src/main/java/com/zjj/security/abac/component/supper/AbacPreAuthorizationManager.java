package com.zjj.security.abac.component.supper;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.authorization.method.MethodAuthorizationDeniedHandler;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class AbacPreAuthorizationManager implements AuthorizationManager<MethodInvocation>, MethodAuthorizationDeniedHandler {



    public AuthorizationDecision check(Supplier<Authentication> authentication, MethodInvocation mi) {
        // ... authorization logic
//        mi.get

        return new AuthorizationDecision(true);
    }

    /**
     * Handle denied method invocations, implementations might either throw an
     * {@link AuthorizationDeniedException} or
     * a replacement result instead of invoking the method, e.g. a masked value.
     *
     * @param methodInvocation    the {@link MethodInvocation} related to the authorization
     *                            denied
     * @param authorizationResult the authorization denied result
     * @return a replacement result for the denied method invocation, or null, or a
     * {@link reactor.core.publisher.Mono} for reactive applications
     */
    @Override
    public Object handleDeniedInvocation(MethodInvocation methodInvocation, AuthorizationResult authorizationResult) {
        return null;
    }
}