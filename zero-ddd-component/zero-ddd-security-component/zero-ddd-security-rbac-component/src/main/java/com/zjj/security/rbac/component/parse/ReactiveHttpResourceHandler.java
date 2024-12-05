package com.zjj.security.rbac.component.parse;

import com.zjj.security.rbac.component.domain.HttpResource;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月05日 21:12
 */
public class ReactiveHttpResourceHandler extends AbstractReactiveResourceHandler {

    @Override
    public boolean matcher(ServerWebExchange exchange) {
        return false;
    }

    @Override
    public Mono<Boolean> handler(ServerWebExchange exchange) {
        reactiveResourceQueryManager.authorize(new HttpResource());
        return null;
    }
}
