package com.zjj.gateway.filter;

import com.zjj.autoconfigure.component.security.jwt.JwtProperties;
import jakarta.annotation.Resource;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月28日 21:38
 */
@Component
public class AfterUserAuthFilter implements GlobalFilter, Ordered {

    @Resource
    private JwtProperties jwtProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        return ReactiveSecurityContextHolder.getContext()
                .map(securityContext -> securityContext.getAuthentication().getName())
                .flatMap(username -> {
                    // Modify the request to include the username header
                    ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                            .header(jwtProperties.getFastToken(), username)
                            .build();
                    // Create a new exchange with the modified request
                    ServerWebExchange modifiedExchange = exchange.mutate()
                            .request(modifiedRequest)
                            .build();
                    // Continue processing the request chain with the modified exchange
                    return chain.filter(modifiedExchange);
                })
                .switchIfEmpty(chain.filter(exchange));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
