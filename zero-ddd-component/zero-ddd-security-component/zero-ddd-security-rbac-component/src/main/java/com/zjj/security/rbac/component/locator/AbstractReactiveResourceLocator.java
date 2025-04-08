package com.zjj.security.rbac.component.locator;

import com.zjj.autoconfigure.component.security.rbac.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月09日 21:47
 */
@Slf4j
public abstract class AbstractReactiveResourceLocator implements ReactiveResourceLocator {

    protected abstract List<Resource> list(@Nullable Object o);
    protected abstract String list1(Resource resource, @Nullable Object o);

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

    @Override
    public Mono<String> load(Resource resource, Mono<Authentication> authentication) throws AuthenticationException {
        return authentication
                .filter(this::isNotAnonymous)
                .map(this::getAuthorizationPrincipal)
                .flatMap(o -> Mono.just(list1(resource, o)))
                .switchIfEmpty(Mono.empty());
    }
}
