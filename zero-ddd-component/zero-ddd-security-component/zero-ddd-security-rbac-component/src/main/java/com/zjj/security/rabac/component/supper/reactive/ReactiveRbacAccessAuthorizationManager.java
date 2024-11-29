package com.zjj.security.rabac.component.supper.reactive;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月26日 15:39
 */
//@RequiredArgsConstructor
public final class ReactiveRbacAccessAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {


//    private final RbacAccessService rbacAccessService;

    /**
     * Determines if access is granted for a specific authentication and object.
     *
     * @param authentication the Authentication to check
     * @param object         the object to check
     * @return an decision or empty Mono if no decision could be made.
     */
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext object) {
//        return authentication
//                .map(auth -> new AuthorizationDecision(true))
//                .or(Mono.just(new AuthorizationDecision(false)));
//        ServerHttpRequest request = object.getExchange().getRequest();
//        return authentication
//                .map(auth -> rbacAccessService.verify(auth.getPrincipal(), auth.getAuthorities(), null))
//                .map(AuthorizationDecision::new);
        Flux<DataBuffer> body = object.getExchange().getRequest().getBody();
        DataBufferUtils.join(body).flatMap(buff -> {
            byte[] bytes = new byte[buff.readableByteCount()];
            buff.read(bytes);
            DataBufferUtils.release(buff);
            return Mono.just(new String(bytes, StandardCharsets.UTF_8));
        }).doOnSuccess(s -> System.out.println(s)).subscribe();
        return Mono.just(new AuthorizationDecision(true));
    }
}
