package com.zjj.security.core.component.supper.reactive;

import com.zjj.autoconfigure.component.security.AuthenticationHelper;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.DelegatingServerAuthenticationEntryPoint;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月27日 21:44
 */
public class DefaultReactiveAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    DefaultReactiveAuthenticationEntryPoint() {}

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        return Mono
                .defer(() -> Mono.just(exchange.getResponse()))
                .flatMap(response -> {
                    DataBuffer buffer = AuthenticationHelper.renderString(response, HttpStatus.FORBIDDEN.value(), "验证失败: " + ex.getCause().getMessage());
                    return response.writeWith(Mono.just(buffer));
                });
    }

    public static ServerAuthenticationEntryPoint handler() {
        return new DefaultReactiveAuthenticationEntryPoint();
    }


}
