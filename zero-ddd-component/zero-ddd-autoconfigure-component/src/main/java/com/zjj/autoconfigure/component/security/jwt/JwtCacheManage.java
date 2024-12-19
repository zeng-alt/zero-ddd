package com.zjj.autoconfigure.component.security.jwt;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月26日 16:36
 */
public interface JwtCacheManage {

	@Nullable
	public UserDetails get(@NonNull String id);

	public <T> T get(String id, Class<T> tClass);

	public void put(@NonNull String id, @NonNull UserDetails userDetails);

    default void remove(String username) {

	}
}
