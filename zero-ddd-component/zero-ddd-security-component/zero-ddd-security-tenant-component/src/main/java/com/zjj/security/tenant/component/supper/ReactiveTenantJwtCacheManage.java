package com.zjj.security.tenant.component.supper;

import com.zjj.autoconfigure.component.redis.RedisStringRepository;
import com.zjj.autoconfigure.component.security.jwt.JwtProperties;
import com.zjj.autoconfigure.component.security.jwt.ReactiveJwtCacheManage;
import com.zjj.autoconfigure.component.tenant.ReactiveTenantContextHolder;
import com.zjj.autoconfigure.component.tenant.TenantContext;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月26日 21:59
 */
public class ReactiveTenantJwtCacheManage implements ReactiveJwtCacheManage {

    private final RedisStringRepository redisStringRepository;
    private final Duration expireTime;

    public ReactiveTenantJwtCacheManage(RedisStringRepository redisStringRepository, JwtProperties jwtProperties) {
        this.redisStringRepository = redisStringRepository;
        this.expireTime = Duration.of(jwtProperties.getExpiration(), jwtProperties.getTemporalUnit());
    }

    private String getKey(String tenant, String id) {
        return "jwt:" + tenant + ":" + id;
    }

    @Override
    @NonNull
    public Mono<UserDetails> get(@NonNull String id) {
        return ReactiveTenantContextHolder
                .getContext()
                .map(TenantContext::getTenant)
                .map(t -> getKey(t, id))
                .flatMap(k -> {
                    UserDetails userDetails = redisStringRepository.get(k);
                    if (userDetails == null) {
                        return Mono.error(new BadCredentialsException("用户登录时间过期，重新登录"));
                    }
                    return Mono.just(userDetails);
                });
    }

    @Override
    @NonNull
    public <T> Mono<T> get(@NonNull String id, @NonNull Class<T> tClass) {
        return ReactiveTenantContextHolder
                .getContext()
                .map(TenantContext::getTenant)
                .map(t -> getKey(t, id))
                .flatMap(k -> {
                    T userDetails = redisStringRepository.get(k);
                    if (userDetails == null) {
                        return Mono.error(new BadCredentialsException("用户登录时间过期，重新登录"));
                    }
                    return Mono.just(userDetails);
                });
    }

    @Override
    @NonNull
    public Mono<Void> put(@NonNull String id, @NonNull UserDetails userDetails) {
        return ReactiveTenantContextHolder
                .getContext()
                .map(TenantContext::getTenant)
                .map(t -> getKey(t, id))
                .flatMap(k -> {
                    redisStringRepository.put(k, userDetails, expireTime);
                    return Mono.empty();
                });
    }

    @Override
    @NonNull
    public Mono<Void> remove(@NonNull String username) {
        return ReactiveTenantContextHolder
                .getContext()
                .map(TenantContext::getTenant)
                .map(t -> getKey(t, username))
                .flatMap(k -> {
                    redisStringRepository.removeAll(k);
                    return Mono.empty();
                });
    }
}
