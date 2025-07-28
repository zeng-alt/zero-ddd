package com.zjj.cache.component.supper;

import com.zjj.autoconfigure.component.UtilException;
import com.zjj.autoconfigure.component.redis.RedisHashRepository;
import com.zjj.autoconfigure.component.security.rbac.GraphqlResource;
import com.zjj.autoconfigure.component.security.rbac.HttpResource;
import com.zjj.autoconfigure.component.security.rbac.RbacCacheManage;
import com.zjj.autoconfigure.component.security.rbac.Resource;
import com.zjj.autoconfigure.component.tenant.TenantKey;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月10日 21:03
 */
@RequiredArgsConstructor
public class RedisRbacCacheManage implements RbacCacheManage, TenantKey, Ordered {

//    private final RedisStringRepository redisStringRepository;
    private final RedisHashRepository  redisHashRepository;

    @Override
    public List<Resource> findAllHttpResource(String username, String tenant, List<String> authority) {
        return findAllResource(HTTP_RESOURCE_KEY, tenant, authority);
    }

    @Override
    public List<Resource> findAllResource(String key, String tenant, List<String> authority) {
        if (CollectionUtils.isEmpty(authority)) return new ArrayList<>();
//        List<String> roleKeys = authority.stream().map(s -> ROLE_KEY + tenant + ":" + s).toList();
//        List<Set<String>> permissionKeys = redisStringRepository.getAll(roleKeys);
//        Set<String> keys = permissionKeys.stream().filter(Objects::nonNull).flatMap(Set::stream).map(s -> key + tenant + ":" + s).collect(Collectors.toSet());
//        List<Resource> result = redisStringRepository.getAll(keys);
//        return result.stream().filter(Objects::nonNull).toList();

        Map<String, Resource> result = redisHashRepository.getAll("rbac:" + getTenantKey() + "http", new HashSet<>(authority));
        return new LinkedList<>();
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
    public void removeRole(String roleName) {
        redisHashRepository.removeNode("rbac:" + getTenantKey() + "role", roleName);
    }

    @Override
    public void removeAllRole() {
        redisHashRepository.remove("rbac:" + getTenantKey() + "role");
    }

    @Override
    public void putRole(Map<String, Set<String>> map, String tenant) {
        redisHashRepository.batchPut("rbac:" + getTenantKey() + "role", new HashMap<>(map));
    }

    @Override
    public void putRole(Map<String, Set<String>> map) {
        redisHashRepository.batchPut("rbac:" + getTenantKey() + "role", new HashMap<>(map));
    }

    @Override
    public void putHttpResource(Map<String, HttpResource> map, String tenant) {
//        redisStringRepository.batchPut(HTTP_RESOURCE_KEY + tenant + ":", map);
        redisHashRepository.batchPut("rbac:" + getTenantKey() + "http", new HashMap<>(map));
    }

    @Override
    public void putGraphqlResource(Map<String, GraphqlResource> map, String tenant) {
//        redisStringRepository.batchPut(GRAPHQL_RESOURCE_KEY + tenant + ":", map);
        redisHashRepository.batchPut("rbac:" + getTenantKey() + "graphql", new HashMap<>(map));
    }


    @Override
    public Set<String> findPermission(List<String> roleName, String tenant) {
        if (CollectionUtils.isEmpty(roleName)) return new HashSet<>();
        final String finalTenant = StringUtils.hasText(tenant) ? tenant + ":" : "";
//        List<String> roleKeys = roleName.stream().map(s -> "rbac:" + finalTenant + "role:" + s).toList();
//        List<Set<String>> result = redisStringRepository.getAll(roleKeys);
//        return result.stream().flatMap(Set::stream).collect(Collectors.toSet());

        Map<String, Set<String>> result = redisHashRepository.getAll("rbac:" + finalTenant + "role", new HashSet<>(roleName));
        return result.entrySet().stream().map(Map.Entry::getValue).flatMap(Set::stream).collect(Collectors.toSet());
    }

    @Override
    public String findPermissionByResource(String tenant, String key) {
        if (!StringUtils.hasText(key)) return "";
        final String finalTenant = StringUtils.hasText(tenant) ? tenant + ":" : "";
//        return redisStringRepository.get("rbac:" + finalTenant + key);

        return redisHashRepository.get("rbac:" + finalTenant+ "http", key);
    }

    @Override
    public Set<String> findPermissionByGraphqlResource(String tenant, Set<Resource> resources) {
        if (CollectionUtils.isEmpty(resources)) return new HashSet<>();
        final String finalTenant = StringUtils.hasText(tenant) ? tenant + ":" : "";
        Set<String> keys = resources.stream().map(Resource::getKey).collect(Collectors.toSet());
        Map<String, String> result = redisHashRepository.getAll("rbac:" + finalTenant+ "graphql", keys);
        return new HashSet<>(result.values());
    }

    public String findPermissionByGraphqlResource(String tenant, String key) {
        if (!StringUtils.hasText(key)) return "";
        final String finalTenant = StringUtils.hasText(tenant) ? tenant + ":" : "";
//        return redisStringRepository.get("rbac:" + finalTenant + key);

        return redisHashRepository.get("rbac:" + finalTenant + "graphql", key);
    }


    @Override
    public void removeAllPermission() {
        redisHashRepository.remove("rbac:" + getTenantKey() + "graphql");
        redisHashRepository.remove("rbac:" + getTenantKey() + "http");
    }

    public void batchPutPermission(Map<String, String> permissionMap) {
        final Map<String, Map<String, Object>> map = new HashMap<>();
        permissionMap.forEach((k, v) -> {
            int i = k.indexOf(":");
            if (i <= 0) {
                throw new UtilException("资源没有前缀");
            }
            String key = k.substring(0, i);
            String key2 = k.substring(i + 1);
            if (map.containsKey(key)) {
                map.get(key).put(key2, v);
            } else {
                Map<String, Object> value = new HashMap<>();
                value.put(key2, v);
                map.put(key, value);
            }
        });
        map.forEach((k, v) -> redisHashRepository.batchPut("rbac:" + getTenantKey() + k, v));
    }

    @Override
    public int getOrder() {
        return 10;
    }
}
