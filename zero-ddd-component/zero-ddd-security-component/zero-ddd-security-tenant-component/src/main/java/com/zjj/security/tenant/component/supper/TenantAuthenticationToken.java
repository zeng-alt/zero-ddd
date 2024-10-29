package com.zjj.security.tenant.component.supper;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.util.Assert;

import java.io.Serial;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月24日 09:52
 */
public class TenantAuthenticationToken extends AbstractAuthenticationToken {
    @Serial
    private static final long serialVersionUID = 1L;

    private Object tenant;

    public TenantAuthenticationToken(Object tenant) {
        super(null);
        this.tenant = tenant;

        this.setAuthenticated(false);
    }

    public static TenantAuthenticationToken authenticated(Object tenant) {
        return new TenantAuthenticationToken(tenant);
    }

    @Override
    public Object getCredentials() {
        return this.tenant;
    }

    @Override
    public Object getPrincipal() {
        return this.tenant;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated,
                "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.tenant = null;
    }
}
