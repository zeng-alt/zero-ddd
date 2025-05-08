package com.zjj.security.rbac.component.manager;

import com.zjj.autoconfigure.component.security.rbac.GraphqlResource;
import com.zjj.autoconfigure.component.security.rbac.Resource;
import com.zjj.security.rbac.component.spi.GraphqlWhiteListService;
import com.zjj.security.rbac.component.spi.ReactiveGraphqlWhiteListAuthorizationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月24日 21:38
 */
@RequiredArgsConstructor
public class DefaultReactiveGraphqlWhiteListAuthorizationManager implements ReactiveGraphqlWhiteListAuthorizationManager {

    private final GraphqlWhiteListService graphqlWhiteListService;

    @Override
    public boolean verify(Set<Resource> graphqlResources) {
        Set<GraphqlResource> whiteList = graphqlWhiteListService.getWhiteList();
        if (CollectionUtils.isEmpty(whiteList)) {
            return false;
        }

        for (Resource graphqlResource : graphqlResources) {
            if (!whiteList.contains((GraphqlResource) graphqlResource)) {
                return false;
            }
        }
        return true;
    }
}
