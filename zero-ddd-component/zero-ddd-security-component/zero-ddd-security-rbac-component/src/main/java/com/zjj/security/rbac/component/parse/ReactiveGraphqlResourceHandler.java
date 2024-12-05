package com.zjj.security.rbac.component.parse;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月05日 17:12
 */
public class ReactiveGraphqlResourceHandler extends AbstractReactiveResourceHandler {
    @Override
    public boolean matcher(ServerWebExchange exchange) {
        return false;
    }

    @Override
    public Mono<Boolean> handler(ServerWebExchange exchange) {
        return null;
    }
}
