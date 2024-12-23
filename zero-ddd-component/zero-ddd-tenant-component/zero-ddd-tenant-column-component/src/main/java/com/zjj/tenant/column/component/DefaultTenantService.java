package com.zjj.tenant.column.component;

import com.zjj.autoconfigure.component.tenant.TenantService;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月23日 14:23
 */
public class DefaultTenantService implements TenantService {

    private final CopyOnWriteArraySet<String> set = new CopyOnWriteArraySet<>();

    @Override
    public void addTenant(String tenant) {
        set.add(tenant);
    }

    @Override
    public Set<String> getTenants() {
        return Collections.unmodifiableSet(set);
    }

    @Override
    public void removeTenant(String tenant) {
        set.remove(tenant);
    }
}
