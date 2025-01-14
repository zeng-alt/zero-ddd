package com.zjj.tenant.domain.menu.event;

import com.zjj.domain.component.event.TenantEvent;
import lombok.Getter;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月11日 21:20
 */
@Getter
public class RemoveMenuEvent extends TenantEvent {

    private final Long menuId;

    protected RemoveMenuEvent(Object o, Long menuId) {
        this.menuId = menuId;
    }

    public static RemoveMenuEvent apply(Object o, Long menuId) {
        return new RemoveMenuEvent(o, menuId);
    }

}
