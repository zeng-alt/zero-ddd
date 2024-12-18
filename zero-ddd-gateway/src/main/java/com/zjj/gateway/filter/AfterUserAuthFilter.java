//package com.zjj.gateway.filter;
//
//import com.zjj.autoconfigure.component.security.jwt.JwtProperties;
//import jakarta.annotation.Resource;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.security.core.context.ReactiveSecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
///**
// * @author zengJiaJun
// * @version 1.0
// * @crateTime 2024年11月28日 21:38
// */
//@Slf4j
//@Component
//public class AfterUserAuthFilter implements GlobalFilter, Ordered {
//
//    @Resource
//    private JwtProperties jwtProperties;
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//
//        return ReactiveSecurityContextHolder.getContext()
//                .map(securityContext -> {
//                    if (securityContext == null || securityContext.getAuthentication() == null) {
//                        // Log the issue and continue with the original exchange
//                        log.warn("Security context or authentication is null");
//                        return exchange;
//                    }
//
//                    String username = securityContext.getAuthentication().getName();
//                    if (username == null) {
//                        // Log the issue and continue with the original exchange
//                        log.warn("Username is null");
//                        return exchange;
//                    }
//
//                    // Modify the request to include the username header
//                    ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
//                            .header(jwtProperties.getFastToken(), username)
//                            .build();
//                    // Create a new exchange with the modified request
//                    return exchange.mutate()
//                            .request(modifiedRequest)
//                            .build();
//                    // Continue processing the request chain with the modified exchange
//                })
//                .switchIfEmpty(Mono.just(exchange))
//                .flatMap(chain::filter);
//    }
//
//    @Override
//    public int getOrder() {
//        return 0;
//    }
//}
