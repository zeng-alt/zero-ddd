package com.zjj.memory.component.supper;

import com.zjj.autoconfigure.component.security.rbac.GraphqlResource;
import com.zjj.autoconfigure.component.security.rbac.HttpResource;
import com.zjj.autoconfigure.component.security.rbac.RbacCacheManage;
import com.zjj.autoconfigure.component.security.rbac.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月10日 11:10
 */
@RequiredArgsConstructor
public class CaffeineRbacCacheManage implements RbacCacheManage {

    private final CacheManager cacheManager;

    @Override
    public List<Resource> findAllHttpResource(String username, String tenant, List<String> authority) {
        Cache cache = cacheManager.getCache(HTTP_RESOURCE_KEY + tenant + ":");
        return null;
    }

    @Override
    public List<Resource> findAllGraphqlResource(String username, String tenant, List<String> authority) {
        return null;
    }

    @Override
    public List<Resource> findAllResource(String key, String tenant, List<String> authority) {
        return null;
    }

    @Override
    public void putRole(String roleName, Set<String> permission, String tenant) {

    }

    @Override
    public void putRole(Map<String, Set<String>> map, String tenant) {

    }

    @Override
    public void putHttpResource(Map<String, HttpResource> map, String tenant) {

    }

    @Override
    public void putGraphqlResource(Map<String, GraphqlResource> map, String tenant) {

    }
}
