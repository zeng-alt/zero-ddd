package com.zjj.security.core.component.supper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.List;
import java.util.function.Supplier;

/**
 * 只要有一个为true就返回AuthorizationDecision(true)
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月27日 21:18
 */
@RequiredArgsConstructor
public class CompositeAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final List<AuthorizationManager<RequestAuthorizationContext>> authorizationManagers;
//    private final AuthenticatedAuthorizationManager<RequestAuthorizationContext> authenticated = AuthenticatedAuthorizationManager.authenticated();


    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {

        for (AuthorizationManager<RequestAuthorizationContext> authorizationManager : authorizationManagers) {
            AuthorizationDecision check = authorizationManager.check(authentication, object);
            if (check != null && check.isGranted()) return check;
        }
        return new AuthorizationDecision(false);
    }
}
