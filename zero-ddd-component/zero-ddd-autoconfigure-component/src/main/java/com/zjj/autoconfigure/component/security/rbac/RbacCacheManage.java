package com.zjj.autoconfigure.component.security.rbac;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月09日 21:21
 */
public interface RbacCacheManage {

    public List<Resource> findAllHttpResource(String username, String tenant, List<String> authority);
    public List<Resource> findAllGraphqlResource(String username, String tenant, List<String> authority);

}
