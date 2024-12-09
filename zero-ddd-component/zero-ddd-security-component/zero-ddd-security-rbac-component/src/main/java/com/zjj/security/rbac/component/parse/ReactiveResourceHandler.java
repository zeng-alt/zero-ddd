package com.zjj.security.rbac.component.parse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月29日 21:20
 */
public interface ReactiveResourceHandler {

    public Mono<ServerWebExchangeMatcher.MatchResult> matcher(ServerWebExchange exchange);


    public Mono<Boolean> handler(ServerWebExchange exchange, Mono<Authentication> authentication);

}