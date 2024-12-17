package com.zjj.security.rbac.component.handler;

import com.zjj.autoconfigure.component.security.rbac.GraphqlResource;
import com.zjj.autoconfigure.component.security.rbac.HttpResource;
import com.zjj.autoconfigure.component.security.rbac.Resource;
import com.zjj.security.rbac.component.manager.ReactiveResourceQueryManager;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月05日 21:12
 */
public class ReactiveHttpResourceHandler extends AbstractReactiveResourceHandler {

    public ReactiveHttpResourceHandler(ReactiveResourceQueryManager reactiveResourceQueryManager) {
        super(reactiveResourceQueryManager);
    }

    @Override
    public Mono<ServerWebExchangeMatcher.MatchResult> matcher(ServerWebExchange exchange) {
        return ServerWebExchangeMatcher.MatchResult.match();
    }

    @Override
    public Mono<Boolean> handler(Mono<Authentication> authentication, AuthorizationContext object) {
//        return reactiveResourceQueryManager
//                .query(new HttpResource(), authentication)
//                .flatMap(resources -> {
//                    Resource targetResource = create(object.getExchange());
//                    if (targetResource == null) {
//                        return Mono.just(false);
//                    }
//                    // 如果resources包含所有的targetResource返回true, 如果有一个没有包含就返回假
//                    Set<Resource> collect = new HashSet<>(resources);
//                    return Mono.just(collect.contains(targetResource));
//                }).switchIfEmpty(Mono.just(false));


        return reactiveResourceQueryManager
                .query(new HttpResource(), authentication)
                .flatMapIterable(resources -> resources)
                .flatMap(resource -> resource.compareTo(object.getExchange()).map(ServerWebExchangeMatcher.MatchResult::isMatch))
                .any(Boolean::booleanValue);
//        return reactiveResourceQueryManager.authorize(create(object.getExchange()), authentication);
    }

    public HttpResource create(ServerWebExchange exchange) {
        HttpResource httpResource = new HttpResource();
        ServerHttpRequest request = exchange.getRequest();
        httpResource.setUri(request.getURI().getPath());
        httpResource.setMethod(request.getMethod().name());
        return httpResource;
    }
}
