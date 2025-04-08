package com.zjj.security.rbac.component.locator;

import com.zjj.autoconfigure.component.security.SecurityUser;
import com.zjj.autoconfigure.component.security.rbac.RbacCacheManage;
import com.zjj.autoconfigure.component.security.rbac.Resource;
import com.zjj.autoconfigure.component.tenant.TenantDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月07日 15:31
 */
@RequiredArgsConstructor
public class ReactivePermissionLocator {

    private final RbacCacheManage rbacCacheManage;

    public Set<String> list(Object o) {
        if (o == null) {
            return new HashSet<>();
        }
        String tenantName = "";
        List<String> authoritys = new ArrayList<>();
        if (o instanceof TenantDetail tenantDetail) {
            tenantName = tenantDetail.getTenantName();
        }
        if (o instanceof SecurityUser securityUser) {
            if (securityUser.getCurrentRole() != null) {
                authoritys = List.of(securityUser.getCurrentRole().getAuthority());
            } else {
                authoritys = securityUser.getRoles().stream().map(GrantedAuthority::getAuthority).toList();
            }
        } else if (o instanceof UserDetails userDetails) {
            authoritys = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        }
        return rbacCacheManage.findPermission(authoritys, tenantName);
    }

    private Object getAuthorizationPrincipal(Authentication authentication) {
        return authentication.getPrincipal();
    }

    private boolean isNotAnonymous(Authentication authentication) {
        return authentication.isAuthenticated();
    }

    public Mono<Set<String>> load(Mono<Authentication> authentication) throws AuthenticationException {
        return authentication
                .filter(this::isNotAnonymous)
                .map(this::getAuthorizationPrincipal)
                .flatMap(o -> Mono.just(list(o)))
                .switchIfEmpty(Mono.empty());
    }
}
