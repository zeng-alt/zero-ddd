package com.zjj.security.rbac.component.supper.reactive;

import com.zjj.security.rbac.component.parse.ReactiveParseManager;
import com.zjj.security.rbac.component.parse.ReactiveResourceHandler;
import graphql.language.Document;
import graphql.parser.Parser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月26日 15:39
 */
@RequiredArgsConstructor
public final class ReactiveRbacAccessAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private final ReactiveParseManager reactiveParseManager;


//    private final RbacAccessService rbacAccessService;

    /**
     * Determines if access is granted for a specific authentication and object.
     *
     * @param authentication the Authentication to check
     * @param object         the object to check
     * @return an decision or empty Mono if no decision could be made.
     */
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext object) {
        ServerWebExchange exchange = object.getExchange();
        return reactiveParseManager
                .parse(object.getExchange())
                .flatMap(r -> r.handler(exchange, authentication))
                .map(AuthorizationDecision::new);

//        return Mono.just(new AuthorizationDecision(true));
    }
}
