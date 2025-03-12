package com.zjj.security.tenant.component.supper.web;

import com.zjj.autoconfigure.component.l2cache.L2Cache;
import com.zjj.autoconfigure.component.l2cache.L2CacheManage;
import com.zjj.autoconfigure.component.security.jwt.JwtCacheManage;
import com.zjj.autoconfigure.component.tenant.TenantContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月25日 15:34
 */
public class TenantJwtL2CacheManage implements JwtCacheManage {

    private final L2Cache<Object, Object> cache;

    public TenantJwtL2CacheManage(L2CacheManage l2CacheManage) {
        if (!l2CacheManage.getCacheNames().contains("jwt")) {
            throw new IllegalArgumentException("jwt cache is null");
        }
        this.cache = l2CacheManage.getL2Cache("jwt");
    }

    @Override
    public UserDetails get(String id) {
        String tenant = TenantContextHolder.getTenantId();
        return cache.get(tenant + ":" + id, UserDetails.class);
    }

    @Override
    public <T> T get(String id, Class<T> tClass) {
        String tenant = TenantContextHolder.getTenantId();
        return cache.get(tenant + ":" + id, tClass);
    }

    @Override
    public void put(String id, UserDetails userDetails) {
        String tenant = TenantContextHolder.getTenantId();
        cache.put(tenant + ":" + id, userDetails);
    }

    @Override
    public void remove(String id) {
        String tenant = TenantContextHolder.getTenantId();
        Set<Object> keys = cache.getKeys();
        List<Object> list = keys.stream().filter(key -> key.toString().startsWith(tenant + ":" + id)).toList();
        cache.clearLocalBatch(list);
    }
}
