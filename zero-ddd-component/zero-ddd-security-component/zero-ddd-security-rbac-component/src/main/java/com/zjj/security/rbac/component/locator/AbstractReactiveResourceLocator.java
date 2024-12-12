package com.zjj.security.rbac.component.locator;

import com.zjj.autoconfigure.component.security.rbac.Resource;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月09日 21:47
 */
@Slf4j
public abstract class AbstractReactiveResourceLocator implements ReactiveResourceLocator {

    protected abstract List<Resource> list(@Nullable Object o);

    protected abstract void verifyInstance(Resource resource);

    private Object getAuthorizationPrincipal(Authentication authentication) {
        return authentication.getPrincipal();
    }

    private boolean isNotAnonymous(Authentication authentication) {
        return authentication.isAuthenticated();
    }

    @Override
    public Mono<List<Resource>> load(Mono<Authentication> authentication) throws AuthenticationException {
        return authentication
                .filter(this::isNotAnonymous)
                .map(this::getAuthorizationPrincipal)
                .flatMap(o -> Mono.just(list(o)))
                .switchIfEmpty(Mono.empty());
    }

    @Deprecated(since = "2.0", forRemoval = true)
    @Override
    public Mono<Boolean> load(Resource resource, Mono<Authentication> authentication) throws AuthenticationException {
        if (authentication == null) {
            return Mono.just(false);
        }
        verifyInstance(resource);
        return authentication
                .filter(this::isNotAnonymous)
                .map(this::getAuthorizationPrincipal)
                .flatMapMany((Function<Object, Publisher<Resource>>) o -> Flux.fromIterable(list(o)))
                .filter(r ->  r.equals(resource))
                .flatMap(r -> Mono.just(true))
                .next()
                .switchIfEmpty(Mono.just(false))
                .onErrorResume(e -> {
                    // 记录异常日志
                    log.error("Error occurred: ", e);
                    return Mono.just(false);
                });
    }

}
