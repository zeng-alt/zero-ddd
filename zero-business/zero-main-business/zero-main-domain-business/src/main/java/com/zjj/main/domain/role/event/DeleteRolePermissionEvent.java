package com.zjj.main.domain.role.event;

import com.zjj.domain.component.event.TenantEvent;
import lombok.Data;
import org.jmolecules.event.annotation.DomainEvent;

import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年05月21日 11:35
 */
@Data
@DomainEvent
public class DeleteRolePermissionEvent extends TenantEvent {
    private String roleCode;
    private Set<Long> permissionId;
}
