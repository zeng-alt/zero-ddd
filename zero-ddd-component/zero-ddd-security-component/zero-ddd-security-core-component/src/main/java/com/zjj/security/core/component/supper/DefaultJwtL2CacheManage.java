package com.zjj.security.core.component.supper;

import com.zjj.autoconfigure.component.l2cache.L2Cache;
import com.zjj.autoconfigure.component.l2cache.L2CacheManage;
import com.zjj.autoconfigure.component.security.jwt.JwtCacheManage;
import com.zjj.autoconfigure.component.security.jwt.JwtProperties;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月25日 14:34
 */
public class DefaultJwtL2CacheManage implements JwtCacheManage {

    private final L2Cache<Object, Object> jwtCache;


    public DefaultJwtL2CacheManage(L2CacheManage l2CacheManage) {
        this.jwtCache = l2CacheManage.getL2Cache("jwt");
    }

    @Override
    public UserDetails get(String id) {
        return jwtCache.get(id, UserDetails.class);
    }


    @Override
    public <T> T get(String id, Class<T> tClass) {
        return jwtCache.get(id, tClass);
    }

    @Override
    public void put(String id, UserDetails userDetails) {

        jwtCache.put(id, userDetails);
    }

    @Override
    public void remove(String username) {
        Set<Object> keys = jwtCache.getKeys();
        List<Object> list = keys.stream().filter(key -> key.toString().startsWith(username)).toList();
        jwtCache.clearLocalBatch(list);
    }
}
