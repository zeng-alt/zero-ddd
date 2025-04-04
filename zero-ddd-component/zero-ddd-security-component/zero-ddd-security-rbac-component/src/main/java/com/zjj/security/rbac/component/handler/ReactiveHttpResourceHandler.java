package com.zjj.security.rbac.component.handler;

import com.zjj.autoconfigure.component.security.rbac.HttpResource;
import com.zjj.security.rbac.component.manager.ReactiveResourceQueryManager;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月05日 21:12
 */
public class ReactiveHttpResourceHandler extends AbstractReactiveResourceHandler {

    public ReactiveHttpResourceHandler(ReactiveResourceQueryManager reactiveResourceQueryManager) {
        super(reactiveResourceQueryManager);
    }

    @Override
    public Mono<ServerWebExchangeMatcher.MatchResult> matcher(ServerWebExchange exchange) {
        return ServerWebExchangeMatcher.MatchResult.match();
    }

    @Override
    public Mono<Boolean> handler(Mono<Authentication> authentication, AuthorizationContext object) {
        return reactiveResourceQueryManager
                .query(new HttpResource(), authentication)
                .flatMapIterable(resources -> resources)
                .flatMap(resource -> resource.compareTo(object.getExchange()).map(ServerWebExchangeMatcher.MatchResult::isMatch))
                .any(Boolean::booleanValue)
                .switchIfEmpty(Mono.just(false));
    }

    public HttpResource create(ServerWebExchange exchange) {
        HttpResource httpResource = new HttpResource();
        ServerHttpRequest request = exchange.getRequest();
        httpResource.setUri(request.getURI().getPath());
        httpResource.setMethod(request.getMethod().name());
        return httpResource;
    }
}
