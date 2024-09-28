package com.zjj.autoconfigure.component.jwt;

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

    public void put(@NonNull String id, @NonNull UserDetails userDetails);
}
