package com.zjj.security.core.component.supper;

import com.zjj.security.core.component.spi.WhiteListService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Set;
import java.util.function.Supplier;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月27日 21:33
 */
@RequiredArgsConstructor
public class WhiteListAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final WhiteListService whiteListService;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        Set<String> whiteList = whiteListService.getWhiteList();
        for (String path : whiteList) {
            if (new AntPathRequestMatcher(path).matches(object.getRequest())) {
                return new AuthorizationDecision(true);
            }
        }
        return new AuthorizationDecision(false);
    }


    public static AuthorizationManager<RequestAuthorizationContext> authenticated(WhiteListService whiteListService) {
        return new WhiteListAuthorizationManager(whiteListService);
    }
}
