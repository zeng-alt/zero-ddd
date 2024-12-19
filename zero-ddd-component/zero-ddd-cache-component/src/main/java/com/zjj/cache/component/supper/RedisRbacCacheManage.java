package com.zjj.cache.component.supper;

import com.zjj.autoconfigure.component.redis.RedisStringRepository;
import com.zjj.autoconfigure.component.security.rbac.GraphqlResource;
import com.zjj.autoconfigure.component.security.rbac.HttpResource;
import com.zjj.autoconfigure.component.security.rbac.RbacCacheManage;
import com.zjj.autoconfigure.component.security.rbac.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月10日 21:03
 */
@RequiredArgsConstructor
public class RedisRbacCacheManage implements RbacCacheManage, Ordered {

    private final RedisStringRepository redisStringRepository;

    @Override
    public List<Resource> findAllHttpResource(String username, String tenant, List<String> authority) {
        return findAllResource(HTTP_RESOURCE_KEY, tenant, authority);
    }

    @Override
    public List<Resource> findAllResource(String key, String tenant, List<String> authority) {
        if (CollectionUtils.isEmpty(authority)) return new ArrayList<>();
        List<String> roleKeys = authority.stream().map(s -> ROLE_KEY + tenant + ":" + s).toList();
        List<Set<String>> permissionKeys = redisStringRepository.getAll(roleKeys);
        Set<String> keys = permissionKeys.stream().flatMap(Set::stream).map(s -> key + tenant + ":" + s).collect(Collectors.toSet());
        List<Resource> result = redisStringRepository.getAll(keys);
        return result.stream().filter(Objects::nonNull).toList();
    }

    @Override
    public List<Resource> findAllGraphqlResource(String username, String tenant, List<String> authority) {
        return findAllResource(GRAPHQL_RESOURCE_KEY, tenant, authority);
    }

    @Override
    public void putRole(String roleName, Set<String> permission, String tenant) {
        putRole(Map.of(roleName, permission), tenant);
    }

    @Override
    public void putRole(Map<String, Set<String>> map, String tenant) {
        redisStringRepository.batchPut(ROLE_KEY + tenant + ":", map);
    }

    @Override
    public void putHttpResource(Map<String, HttpResource> map, String tenant) {
        redisStringRepository.batchPut(HTTP_RESOURCE_KEY + tenant + ":", map);
    }

    @Override
    public void putGraphqlResource(Map<String, GraphqlResource> map, String tenant) {
        redisStringRepository.batchPut(GRAPHQL_RESOURCE_KEY + tenant + ":", map);
    }

    @Override
    public int getOrder() {
        return 10;
    }
}
