package com.zjj.main.domain.role.event;

import com.zjj.domain.component.event.TenantEvent;
import lombok.Data;
import org.jmolecules.event.annotation.DomainEvent;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月17日 11:31
 */
@Data
@DomainEvent
public class DeleteRoleEvent extends TenantEvent {
    private String roleCode;

    public static DeleteRoleEvent of(String code) {
        DeleteRoleEvent event = new DeleteRoleEvent();
        event.roleCode = code;
        return event;
    }
}
