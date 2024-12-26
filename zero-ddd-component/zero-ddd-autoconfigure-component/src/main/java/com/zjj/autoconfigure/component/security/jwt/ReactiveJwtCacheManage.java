package com.zjj.autoconfigure.component.security.jwt;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月26日 21:57
 */
public interface ReactiveJwtCacheManage {

    @NonNull
    public Mono<UserDetails> get(@NonNull String id);

    public <T> Mono<T> get(String id, Class<T> tClass);

    public Mono<Void> put(@NonNull String id, @NonNull UserDetails userDetails);

    default Mono<Void> remove(String username) {
        return Mono.empty();
    }
}
