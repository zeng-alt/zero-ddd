package com.zjj.security.rbac.component.spi;

import com.zjj.autoconfigure.component.security.rbac.GraphqlResource;
import com.zjj.autoconfigure.component.security.rbac.Resource;

import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月24日 21:34
 */
public interface ReactiveGraphqlWhiteListAuthorizationManager {

    public boolean verify(Set<Resource> graphqlResources);
}
