package com.zjj.security.core.component.supper.reactive;

import com.zjj.autoconfigure.component.security.AuthenticationHelper;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月27日 21:44
 */
public class DefaultReactiveAccessDeniedHandler implements ServerAccessDeniedHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.FORBIDDEN);
        DataBuffer dataBuffer = AuthenticationHelper.renderString(exchange.getResponse(), exchange.getRequest(),  HttpStatus.FORBIDDEN.value(), "访问拒绝: " + denied.getMessage());
        return exchange.getResponse().writeWith(Mono.just(dataBuffer));
    }

    public static ServerAccessDeniedHandler handler() {
        return new DefaultReactiveAccessDeniedHandler();
    }
}
