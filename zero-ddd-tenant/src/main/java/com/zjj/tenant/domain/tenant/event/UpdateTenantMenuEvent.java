package com.zjj.tenant.domain.tenant.event;

import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月04日 15:37
 */
public class UpdateTenantMenuEvent extends TenantEvent {

    private final String tenantKey;
    private final Set<Long> menuIds;

    protected UpdateTenantMenuEvent(Object o, String tenantKey, Set<Long> menuIds) {
        this.tenantKey = tenantKey;
        this.menuIds = menuIds;
    }


    public static UpdateTenantMenuEvent apply(Object o, String tenantKey, Set<Long> menuIds) {
        return new UpdateTenantMenuEvent(o, tenantKey, menuIds);
    }
}
