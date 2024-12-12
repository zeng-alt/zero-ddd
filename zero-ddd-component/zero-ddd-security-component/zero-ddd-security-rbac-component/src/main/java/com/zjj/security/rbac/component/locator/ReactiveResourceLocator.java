package com.zjj.security.rbac.component.locator;

import com.zjj.autoconfigure.component.security.rbac.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月06日 21:54
 */
public interface ReactiveResourceLocator {

    Mono<Boolean> load(Resource resource, Mono<Authentication> authentication) throws AuthenticationException;

    Mono<List<Resource>> load(Mono<Authentication> authentication) throws AuthenticationException;

    boolean supports(Class<?> resource);
}
