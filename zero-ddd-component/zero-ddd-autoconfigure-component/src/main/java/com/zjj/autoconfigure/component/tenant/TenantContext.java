package com.zjj.autoconfigure.component.tenant;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月26日 21:30
 */
public interface TenantContext {

    String getTenant();

    void setTenant(String tenant);
}
