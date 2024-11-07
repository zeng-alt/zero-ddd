package com.zjj.tenant.domain.menu.event;

import com.zjj.domain.component.DomainEvent;


/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月05日 20:17
 */
public class DisableMenuEvent extends DomainEvent {

    private final Long menuId;

    public DisableMenuEvent(Object o, long menuId) {
        super(o);
        this.menuId = menuId;
    }

    public static DisableMenuEvent apply(Object o, long menuId) {
        return new DisableMenuEvent(o, menuId);
    }
}
