package com.zjj.security.rbac.component.locator;

import com.zjj.autoconfigure.component.security.rbac.Resource;
import org.springframework.core.Ordered;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月06日 21:54
 */
public interface ReactiveResourceLocator extends Ordered {


    Mono<List<Resource>> load(Mono<Authentication> authentication) throws AuthenticationException;

    default Mono<String> load(Resource resource, Mono<Authentication> authentication) throws AuthenticationException {
        return Mono.empty();
    }

    default Mono<Set<String>> load(Set<Resource> resource, Mono<Authentication> authentication) throws AuthenticationException {
        return Mono.empty();
    }

    boolean supports(Class<?> resource);
}
