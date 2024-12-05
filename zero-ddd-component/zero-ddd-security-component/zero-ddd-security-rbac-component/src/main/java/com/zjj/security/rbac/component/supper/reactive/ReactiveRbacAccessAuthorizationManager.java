package com.zjj.security.rbac.component.supper.reactive;

import com.zjj.security.rbac.component.parse.ReactiveParseManager;
import com.zjj.security.rbac.component.parse.ReactiveResourceHandler;
import graphql.language.Document;
import graphql.parser.Parser;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.web.server.ServerWebExchange;
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

    private ReactiveParseManager reactiveParseManager;

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

//        ExecutionInput executionInput = ExecutionInput.newExecutionInput().variables(variable).query("query userQuery($iidd:[Int],$dogId:Int){user(id:$iidd){id,age,dogs(dogId:$dogId){id,dogName}}}").build();
////DataFetcher
//        List<Integer> id = environment.getArgument("id");
        ServerWebExchange exchange = object.getExchange();
        return reactiveParseManager
                .parse(object.getExchange())
                .flatMap(r -> r.handler(exchange))
                .map(AuthorizationDecision::new);

//        object.getExchange();
//        ServerHttpRequest request = object.getExchange().getRequest();
//        Flux<DataBuffer> body = object.getExchange().getRequest().getBody();
//        DataBufferUtils.join(body).flatMap(buff -> {
//            byte[] bytes = new byte[buff.readableByteCount()];
//            buff.read(bytes);
//            DataBufferUtils.release(buff);
//
//            String s = new String(bytes, StandardCharsets.UTF_8);
//            return Mono.just(new String(bytes, StandardCharsets.UTF_8));
//        }).doOnSuccess(s -> System.out.println(s)).subscribe();
//        return Mono.just(new AuthorizationDecision(true));
    }
}
