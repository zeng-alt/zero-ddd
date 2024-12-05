package com.zjj.security.rbac.component.parse;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月29日 21:15
 */
public class ReactiveParseManager {


    private List<ReactiveResourceHandler> reactiveResourceHandlers;


    public Mono<ReactiveResourceHandler> parse(ServerWebExchange exchange) {
        for (ReactiveResourceHandler reactiveResourceHandler : reactiveResourceHandlers) {
            if (reactiveResourceHandler.matcher(exchange)) {
                return Mono.just(reactiveResourceHandler);
            }
        }
        return Mono.empty();
    }
}
