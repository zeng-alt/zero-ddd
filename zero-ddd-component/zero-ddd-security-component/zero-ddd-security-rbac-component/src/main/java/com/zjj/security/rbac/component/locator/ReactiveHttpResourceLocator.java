package com.zjj.security.rbac.component.locator;

import com.zjj.autoconfigure.component.security.rbac.RbacCacheManage;
import com.zjj.autoconfigure.component.tenant.TenantDetail;
import com.zjj.autoconfigure.component.security.rbac.HttpResource;
import com.zjj.autoconfigure.component.security.rbac.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月06日 21:09
 */
@RequiredArgsConstructor
public class ReactiveHttpResourceLocator extends AbstractReactiveResourceLocator {

    private final RbacCacheManage rbacCacheManage;

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
        return rbacCacheManage.findAllHttpResource(username, tenantName, authoritys);
    }

    @Override
    protected String list1(Resource resource, Object o) {

        if (o == null) {
            return "";
        }
        String tenantName = null;

        if (o instanceof TenantDetail tenantDetail) {
            tenantName = tenantDetail.getTenantName();
        }
        String permission = rbacCacheManage.findPermissionByResource(tenantName, resource.getKey());
        return permission == null ? "" : permission;
    }

    @Override
    protected void verifyInstance(Resource resource) {
        Assert.isInstanceOf(HttpResource.class, resource,"Only HttpResource is supported");
    }

    @Override
    public boolean supports(Class<?> resource) {
        return (HttpResource.class.isAssignableFrom(resource));
    }


    @Override
    public int getOrder() {
        return 30;
    }
}
