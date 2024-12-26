package com.zjj.autoconfigure.component.tenant;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月26日 21:31
 */
@EqualsAndHashCode
@ToString
public class TenantContextImpl implements TenantContext {

    private String tenant;

    public TenantContextImpl() {
    }

    public TenantContextImpl(String tenant) {
        this.tenant = tenant;
    }

    @Override
    public String getTenant() {
        return tenant;
    }

    @Override
    public void setTenant(String tenant) {
        this.tenant = tenant;
    }
}
