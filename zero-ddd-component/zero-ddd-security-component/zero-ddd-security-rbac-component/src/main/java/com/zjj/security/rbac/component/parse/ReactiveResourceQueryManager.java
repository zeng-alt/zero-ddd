package com.zjj.security.rbac.component.parse;

import com.zjj.autoconfigure.component.security.rbac.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月05日 21:14
 */
@RequiredArgsConstructor
public class ReactiveResourceQueryManager {

    private final List<ReactiveResourceLocator> resourceLocators;

    public Mono<Boolean> authorize(Resource resource, Mono<Authentication> authentication) {
        return Flux
                .fromIterable(this.resourceLocators)
                .filter(locator -> locator.supports(resource.getClass()))
                .flatMap(locator -> locator.load(resource, authentication))
                .next()
                .switchIfEmpty(Mono.just(false));
    }
}
