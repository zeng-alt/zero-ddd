package com.zjj.tenant.domain.tenant.event;

import com.zjj.domain.component.DomainEvent;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月04日 21:45
 */
public class TenantEvent extends DomainEvent {
    protected TenantEvent(Object o) {
        super(o);
    }
}
