package com.zjj.security.rbac.component.locator;

import com.zjj.autoconfigure.component.security.rbac.GraphqlResource;
import com.zjj.autoconfigure.component.security.rbac.RbacCacheManage;
import com.zjj.autoconfigure.component.security.rbac.Resource;
import com.zjj.autoconfigure.component.tenant.TenantDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月11日 21:53
 */
@RequiredArgsConstructor
public class GraphqlResourceLocator extends AbstractResourceLocator {

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
    public boolean supports(Class<?> resource) {
        return (GraphqlResource.class.isAssignableFrom(resource));
    }
}
