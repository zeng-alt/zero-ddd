package com.zjj.tenant.domain.menu.event;

import com.zjj.domain.component.DomainEvent;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月05日 20:17
 */
public class EnableMenuEvent extends DomainEvent {

    private final Long menuId;

    public EnableMenuEvent(Object o, long menuId) {
        super(o);
        this.menuId = menuId;
    }

    public static EnableMenuEvent apply(Object o, long menuId) {
        return new EnableMenuEvent(o, menuId);
    }
}
