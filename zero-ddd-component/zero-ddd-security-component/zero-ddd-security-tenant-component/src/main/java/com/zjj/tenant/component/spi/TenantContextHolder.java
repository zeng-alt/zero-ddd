package com.zjj.tenant.component.spi;

import org.springframework.lang.Nullable;

import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月23日 20:49
 */
public interface TenantContextHolder {

    public void clearTenant();
    public Set<String> getTenants();
    @Nullable
    public String getCurrentTenant();
    public void setCurrentTenant(String tenant);
}
