package com.zjj.security.core.component.supper.reactive;

import com.zjj.autoconfigure.component.json.JsonUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月27日 21:44
 */
public class DefaultReactiveAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    DefaultReactiveAuthenticationEntryPoint() {}

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        ServerHttpResponse response = exchange.getResponse();

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        DataBufferFactory dataBufferFactory = response.bufferFactory();
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
        problemDetail.setInstance(exchange.getRequest().getURI());
        problemDetail.setTitle("验证失败");

        DataBuffer dataBuffer = dataBufferFactory.wrap(JsonUtils.toJsonString(problemDetail).getBytes(StandardCharsets.UTF_8));

        return exchange.getResponse().writeWith(Mono.just(dataBuffer));
    }

    public static ServerAuthenticationEntryPoint handler() {
        return new DefaultReactiveAuthenticationEntryPoint();
    }


}
