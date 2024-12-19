package com.zjj.l2.cache.component.supper;

import com.zjj.autoconfigure.component.security.rbac.GraphqlResource;
import com.zjj.autoconfigure.component.security.rbac.HttpResource;
import com.zjj.autoconfigure.component.security.rbac.RbacCacheManage;
import com.zjj.autoconfigure.component.security.rbac.Resource;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月19日 16:49
 */
public class L2RbacCacheManage implements RbacCacheManage {
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
