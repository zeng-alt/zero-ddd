package com.zjj.security.core.component.supper.reactive;

import com.zjj.autoconfigure.component.security.AuthenticationHelper;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
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
        return Mono
                .defer(() -> Mono.just(exchange.getResponse()))
                .flatMap(response -> {
                    DataBuffer buffer = AuthenticationHelper.renderString(response, HttpStatus.FORBIDDEN.value(), "访问拒绝: " + denied.getMessage());
                    return response.writeWith(Mono.just(buffer)).doOnError(error -> DataBufferUtils.release(buffer));
                });
    }

    public static ServerAccessDeniedHandler handler() {
        return new DefaultReactiveAccessDeniedHandler();
    }
}
