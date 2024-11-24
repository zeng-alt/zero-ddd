package com.zjj.tenant.domain.tenant.event;

import lombok.Getter;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月08日 21:47
 */
@Getter
public class DisableTenantMenuEvent extends TenantEvent {

    private final Long menuId;
    private final String tenantKey;


    protected DisableTenantMenuEvent(Object o, Long menuId, String tenantKey) {
        super(o);
        this.menuId = menuId;
        this.tenantKey = tenantKey;
    }

    public static DisableTenantMenuEvent apply(Object o, Long menuId, String tenantKey) {
        return new DisableTenantMenuEvent(o, menuId, tenantKey);
    }
}
