package com.zjj.main.domain.user.event;

import com.zjj.domain.component.event.TenantEvent;
import lombok.Data;
import org.jmolecules.event.annotation.DomainEvent;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年05月21日 20:46
 */
@Data
@DomainEvent
public class StockInUserRoleEvent extends TenantEvent {
    private Long userId;
    private Long roleId;

    public static StockInUserRoleEvent of(Long userId, Long roleId) {
        StockInUserRoleEvent event = new StockInUserRoleEvent();
        event.setUserId(userId);
        event.setRoleId(roleId);
        return event;
    }
}
