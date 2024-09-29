package com.zjj.security.jwt.component.supper;

import com.zjj.autoconfigure.component.security.jwt.JwtCacheManage;
import com.zjj.security.jwt.component.configuration.JwtProperties;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月29日 20:54
 */
public class DefaultJwtCacheManage implements JwtCacheManage {

    private final JwtProperties jwtProperties;

    public DefaultJwtCacheManage(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    public UserDetails get(String id) {
        return null;
    }

    @Override
    public void put(String id, UserDetails userDetails) {

    }
}