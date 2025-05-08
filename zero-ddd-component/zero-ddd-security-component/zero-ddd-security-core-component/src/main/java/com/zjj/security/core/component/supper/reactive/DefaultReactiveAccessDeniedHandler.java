package com.zjj.security.core.component.supper.reactive;

import com.zjj.autoconfigure.component.json.JsonUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;


/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月27日 21:44
 */
public class DefaultReactiveAccessDeniedHandler implements ServerAccessDeniedHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        ServerHttpResponse response = exchange.getResponse();
//        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.FORBIDDEN);

        DataBufferFactory dataBufferFactory = response.bufferFactory();

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, denied.getMessage());
        problemDetail.setInstance(exchange.getRequest().getURI());
        problemDetail.setTitle("访问拒绝");

        DataBuffer dataBuffer = dataBufferFactory.wrap(JsonUtils.toJsonString(problemDetail).getBytes(StandardCharsets.UTF_8));
        return exchange.getResponse().writeWith(Mono.just(dataBuffer));
    }

    public static ServerAccessDeniedHandler handler() {
        return new DefaultReactiveAccessDeniedHandler();
    }
}
