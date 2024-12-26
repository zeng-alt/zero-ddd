package com.zjj.security.core.component.supper.reactive;

import com.zjj.autoconfigure.component.redis.RedisStringRepository;
import com.zjj.autoconfigure.component.security.jwt.JwtProperties;
import com.zjj.autoconfigure.component.security.jwt.ReactiveJwtCacheManage;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月26日 21:06
 */
public class DefaultReactiveJwtCacheManage implements ReactiveJwtCacheManage {

    private final RedisStringRepository redisStringRepository;
    private final Duration expireTime;

    public DefaultReactiveJwtCacheManage(RedisStringRepository redisStringRepository, JwtProperties jwtProperties) {
        this.redisStringRepository = redisStringRepository;
        this.expireTime = Duration.of(jwtProperties.getExpiration(), jwtProperties.getTemporalUnit());
    }

    private String getKey(String id) {
        return "jwt:" + id;
    }

    @Override
    public Mono<UserDetails> get(String id) {
        return Mono.create(userMonoSink -> {
            try {
                userMonoSink.success(redisStringRepository.get(getKey(id)));
            } catch (Exception e) {
                userMonoSink.error(e);
            }
        });
    }

    @Override
    public <T> Mono<T> get(String id, Class<T> tClass) {
        return Mono.create(userMonoSink -> {
            try {
                userMonoSink.success(redisStringRepository.get(getKey(id)));
            } catch (Exception e) {
                userMonoSink.error(e);
            }
        });
    }

    @Override
    public Mono<Void> put(String id, UserDetails userDetails) {
        return Mono.create(userMonoSink -> {
            try {
                redisStringRepository.put(getKey(id), userDetails, expireTime);
                userMonoSink.success();
            } catch (Exception e) {
                userMonoSink.error(e);
            }
        });
    }

    @Override
    public Mono<Void> remove(String username) {
        return Mono.create(userMonoSink -> {
            try {
                redisStringRepository.remove(getKey(username));
                userMonoSink.success();
            } catch (Exception e) {
                userMonoSink.error(e);
            }
        });
    }
}
