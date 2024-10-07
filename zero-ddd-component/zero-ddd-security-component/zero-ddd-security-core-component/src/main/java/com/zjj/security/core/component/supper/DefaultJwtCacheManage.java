package com.zjj.security.core.component.supper;

import com.zjj.autoconfigure.component.security.jwt.JwtCacheManage;
import com.zjj.autoconfigure.component.security.jwt.JwtProperties;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月29日 20:54
 */
public class DefaultJwtCacheManage implements JwtCacheManage {

    private final Map<String, UserDetails> map = new HashMap<>();
    private final JwtProperties jwtProperties;

    public DefaultJwtCacheManage(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    public UserDetails get(String id) {
        return map.getOrDefault(id, null);
    }

    @Override
    public void put(String id, UserDetails userDetails) {
        map.put(id, userDetails);
    }
}
