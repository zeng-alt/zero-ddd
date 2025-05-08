package com.zjj.security.rbac.component.locator;

import com.zjj.autoconfigure.component.security.rbac.GraphqlResource;
import com.zjj.autoconfigure.component.security.rbac.RbacCacheManage;
import com.zjj.autoconfigure.component.security.rbac.Resource;
import com.zjj.autoconfigure.component.tenant.TenantDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月06日 21:09
 */
@RequiredArgsConstructor
public class ReactiveGraphqlResourceLocator extends AbstractReactiveResourceLocator {


    private final RbacCacheManage rbacCacheManage;

    @Override
    protected List<Resource> list(Object o) {
        if (o == null) {
            return new ArrayList<>();
        }
        String tenantName = null;
        String username = "";
        List<String> authoritys = new ArrayList<>();
        if (o instanceof TenantDetail tenantDetail) {
            tenantName = tenantDetail.getTenantName();
        }
        if (o instanceof UserDetails userDetails) {
            username = userDetails.getUsername();
            authoritys = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        }
        return rbacCacheManage.findAllGraphqlResource(username, tenantName, authoritys);
    }

    @Override
    protected String list1(Resource resource, Object o) {
        return null;
    }


    @Override
    protected Set<String> list1(Set<Resource> resource, Object o) {
        if (o == null) {
            return new HashSet<>();
        }

        String tenantName = null;

        if (o instanceof TenantDetail tenantDetail) {
            tenantName = tenantDetail.getTenantName();
        }
        Set<String> permission = rbacCacheManage.findPermissionByGraphqlResource(tenantName, resource);
        return CollectionUtils.isEmpty(permission) ? new HashSet<>() : permission;
    }

    @Override
    protected void verifyInstance(Resource resource) {
        Assert.isInstanceOf(GraphqlResource.class, resource,"Only GraphqlResource is supported");
    }

    @Override
    public boolean supports(Class<?> resource) {
        return (GraphqlResource.class.isAssignableFrom(resource));
    }


    /**
     * Get the order value of this object.
     * <p>Higher values are interpreted as lower priority. As a consequence,
     * the object with the lowest value has the highest priority (somewhat
     * analogous to Servlet {@code load-on-startup} values).
     * <p>Same order values will result in arbitrary sort positions for the
     * affected objects.
     *
     * @return the order value
     * @see #HIGHEST_PRECEDENCE
     * @see #LOWEST_PRECEDENCE
     */
    @Override
    public int getOrder() {
        return 10;
    }
}
