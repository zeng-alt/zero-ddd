package com.zjj.memory.component.supper;

import com.github.benmanes.caffeine.cache.Cache;
import com.zjj.autoconfigure.component.security.rbac.GraphqlResource;
import com.zjj.autoconfigure.component.security.rbac.HttpResource;
import com.zjj.autoconfigure.component.security.rbac.RbacCacheManage;
import com.zjj.autoconfigure.component.security.rbac.Resource;
import org.springframework.cache.caffeine.CaffeineCacheManager;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月10日 11:10
 */
public class CaffeineRbacCacheManage implements RbacCacheManage {

    private final Cache roleCache;
    private final Cache httpCache;
    private final Cache graphqlCache;

    public CaffeineRbacCacheManage(CaffeineCacheManager cacheManager) {
        roleCache = (Cache) cacheManager.getCache(RbacCacheManage.ROLE_KEY);
        httpCache = (Cache) cacheManager.getCache(RbacCacheManage.HTTP_RESOURCE_KEY);
        graphqlCache = (Cache) cacheManager.getCache(RbacCacheManage.GRAPHQL_RESOURCE_KEY);
    }

    @Override
    public List<Resource> findAllHttpResource(String username, String tenant, List<String> authority) {

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
