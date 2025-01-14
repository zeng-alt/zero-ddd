package com.zjj.tenant.domain.tenant.event;

import com.zjj.domain.component.event.TenantEvent;
import lombok.Getter;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月07日 21:27
 */
@Getter
public class DisableTenantEvent  extends TenantEvent {

    private final String tenantKey;

    public DisableTenantEvent(Object o, String tenantKey) {
        this.tenantKey = tenantKey;
    }

    public static DisableTenantEvent apply(Object o, String tenantKey) {
        return new DisableTenantEvent(o, tenantKey);
    }
}
