package com.zjj.tenant.domain.tenant.event;

import lombok.Getter;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月08日 16:47
 */
@Getter
public class EnableTenantMenuEvent extends TenantEvent {

    private final Long menuId;
    private final String tenantKey;


    protected EnableTenantMenuEvent(Object o, Long menuId, String tenantKey) {
        super(o);
        this.menuId = menuId;
        this.tenantKey = tenantKey;
    }

    public static EnableTenantMenuEvent apply(Object o, Long menuId, String tenantKey) {
        return new EnableTenantMenuEvent(o, menuId, tenantKey);
    }
}
