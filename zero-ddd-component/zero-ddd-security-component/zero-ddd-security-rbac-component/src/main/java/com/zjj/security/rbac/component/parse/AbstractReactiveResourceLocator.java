package com.zjj.security.rbac.component.parse;

import com.zjj.autoconfigure.component.security.rbac.Resource;
import org.reactivestreams.Publisher;
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
public abstract class AbstractReactiveResourceLocator implements ReactiveResourceLocator {

    protected abstract List<Resource> list(Object o);

    protected abstract void verifyInstance(Resource resource);

    @Override
    public Mono<Boolean> load(Resource resource, Mono<Authentication> authentication) throws AuthenticationException {
        verifyInstance(resource);
        return authentication
                .map(Authentication::getDetails)
                .flatMapMany((Function<Object, Publisher<Resource>>) o -> Flux.fromIterable(list(o)))
                .filter(r ->  r.equals(resource))
                .flatMap(r -> Mono.just(true))
                .next()
                .switchIfEmpty(Mono.just(false));
    }

}
