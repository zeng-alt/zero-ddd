package com.zjj.security.core.component.supper;

import com.zjj.autoconfigure.component.redis.RedisStringRepository;
import com.zjj.autoconfigure.component.security.jwt.JwtCacheManage;
import com.zjj.autoconfigure.component.security.jwt.JwtProperties;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月29日 20:54
 */
public class DefaultJwtCacheManage implements JwtCacheManage {

	private final RedisStringRepository redisStringRepository;
	private final Duration expireTime;

    public DefaultJwtCacheManage(RedisStringRepository redisStringRepository, JwtProperties jwtProperties) {
        this.redisStringRepository = redisStringRepository;
        this.expireTime = Duration.of(jwtProperties.getExpiration(), jwtProperties.getTemporalUnit());
    }

	private String getKey(String id) {
		return "jwt:" + id;
	}

    @Override
	public UserDetails get(@NonNull String id) {
		return redisStringRepository.get(getKey(id));
	}

	@Override
	public <T> T get(String id, Class<T> tClass) {
		return redisStringRepository.get(getKey(id));
	}

	@Override
	public void put(String id, UserDetails userDetails) {
		redisStringRepository.put(getKey(id), userDetails, expireTime);
	}
	@Override
	public void remove(String username) {
		redisStringRepository.removeAll(getKey(username));
	}
}
