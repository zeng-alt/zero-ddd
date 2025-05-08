package com.zjj.autoconfigure.component.security.rbac;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月09日 21:21
 */
public interface RbacCacheManage {

    String PRE_KEY = "rbac:";
    String ROLE_KEY = PRE_KEY + "role:";
    String HTTP_RESOURCE_KEY = PRE_KEY + "http:";
    String GRAPHQL_RESOURCE_KEY = PRE_KEY + "graphql:";

    public List<Resource> findAllHttpResource(String username, String tenant, List<String> authority);
    public List<Resource> findAllGraphqlResource(String username, String tenant, List<String> authority);

    public List<Resource> findAllResource(String key, String tenant, List<String> authority);

    public void putRole(String roleName, Set<String> permission, String tenant);

    public void putRole(Map<String, Set<String>> map, String tenant);

    public void putRole(Map<String, Set<String>> map);

    public void putHttpResource(Map<String, HttpResource> map, String tenant);

    public void putGraphqlResource(Map<String, GraphqlResource> map, String tenant);

    default void putRole(String roleName, Set<String> permission) {
        putRole(Map.of(roleName, permission), null);
    }

    public String findPermissionByGraphqlResource(String tenant, String key);

    default void putHttpResource(Map<String, HttpResource> map) {
        putHttpResource(map, null);
    }

    default void putGraphqlResource(Map<String, GraphqlResource> map) {
        putGraphqlResource(map, null);
    }

    default Set<String> findPermission(List<String> roleName, String tenant) {
        return new HashSet<>();
    }

    default String findPermissionByResource(String tenantName, String key) {
        return null;
    }

    default Set<String> findPermissionByGraphqlResource(String tenantName, Set<Resource> resources) {
        return null;
    }

    void batchPutPermission(Map<String, String> permissionMap);
}
