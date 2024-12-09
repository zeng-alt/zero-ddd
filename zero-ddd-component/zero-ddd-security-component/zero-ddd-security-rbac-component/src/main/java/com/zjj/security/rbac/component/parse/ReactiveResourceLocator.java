package com.zjj.security.rbac.component.parse;

import com.zjj.autoconfigure.component.security.rbac.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import reactor.core.publisher.Mono;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月06日 21:54
 */
public interface ReactiveResourceLocator {

    Mono<Boolean> load(Resource resource, Mono<Authentication> authentication) throws AuthenticationException;

    boolean supports(Class<?> resource);
}
