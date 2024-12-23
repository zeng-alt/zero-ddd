package com.zjj.security.tenant.component.supper;

import com.zjj.autoconfigure.component.redis.RedisStringRepository;
import com.zjj.autoconfigure.component.security.jwt.JwtCacheManage;
import com.zjj.autoconfigure.component.security.jwt.JwtProperties;
import com.zjj.autoconfigure.component.tenant.TenantContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月25日 15:30
 */

public class TenantJwtCacheManage implements JwtCacheManage {

    private final RedisStringRepository redisStringRepository;
    private final Duration expireTime;

    public TenantJwtCacheManage(RedisStringRepository redisStringRepository, JwtProperties jwtProperties) {
        this.redisStringRepository = redisStringRepository;
        this.expireTime = Duration.of(jwtProperties.getExpiration(), jwtProperties.getTemporalUnit());
    }

    @Override
    public UserDetails get(String id) {
        return redisStringRepository.get(TenantContextHolder.getTenantId() + ":jwt:" + id);
    }

    @Override
    public <T> T get(String id, Class<T> tClass) {
        return redisStringRepository.get(TenantContextHolder.getTenantId() + ":jwt:" + id);
    }

    @Override
    public void put(String id, UserDetails userDetails) {
        redisStringRepository.put(TenantContextHolder.getTenantId() + ":jwt:" + id, userDetails, expireTime);
    }

    @Override
    public void remove(String username) {
        redisStringRepository.removeAll(TenantContextHolder.getTenantId() + ":jwt:" + username);
    }
}
