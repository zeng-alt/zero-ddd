package com.zjj.security.rbac.component.parse;

import com.zjj.autoconfigure.component.security.rbac.HttpResource;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
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
    public Mono<Boolean> handler(ServerWebExchange exchange, Mono<Authentication> authentication) {
        return reactiveResourceQueryManager.authorize(create(exchange), authentication);
    }

    public HttpResource create(ServerWebExchange exchange) {
        HttpResource httpResource = new HttpResource();
        ServerHttpRequest request = exchange.getRequest();
        httpResource.setUri(request.getURI().getPath());
        httpResource.setMethod(request.getMethod());
        return httpResource;
    }
}
