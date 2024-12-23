package com.zjj.autoconfigure.component.tenant;

import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月23日 21:24
 */
public interface TenantService {

    public void addTenant(String tenant);

    public Set<String> getTenants();

    public void removeTenant(String tenant);
}
