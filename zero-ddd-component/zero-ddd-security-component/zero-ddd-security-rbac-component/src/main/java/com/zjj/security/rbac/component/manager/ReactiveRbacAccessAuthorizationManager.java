package com.zjj.security.rbac.component.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import reactor.core.publisher.Mono;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月26日 15:39
 */
@RequiredArgsConstructor
public final class ReactiveRbacAccessAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private final ReactiveParseManager reactiveParseManager;


    /**
     * Determines if access is granted for a specific authentication and object.
     *
     * @param authentication the Authentication to check
     * @param object         the object to check
     * @return an decision or empty Mono if no decision could be made.
     */
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext object) {
        return reactiveParseManager
                .parse(object.getExchange())
                .flatMap(r -> r.handler(authentication, object))
                .map(AuthorizationDecision::new);

//        return Mono.just(new AuthorizationDecision(true));
    }
}
