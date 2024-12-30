package com.zjj.security.core.component.supper;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月27日 21:25
 */
public class CompositeReactiveAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    private final List<ReactiveAuthorizationManager<AuthorizationContext>> managers;

    public CompositeReactiveAuthorizationManager(List<ReactiveAuthorizationManager<AuthorizationContext>> managers) {
        this.managers = managers;
    }


    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext context) {
        return Flux.fromIterable(managers)
                .flatMap(manager -> manager.check(authentication, context))
                .filter(AuthorizationDecision::isGranted)
                .hasElements()
                .map(AuthorizationDecision::new)
                .switchIfEmpty(Mono.just(new AuthorizationDecision(false)));
    }
}
