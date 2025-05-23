package com.zjj.main.domain.role.event;

import com.zjj.domain.component.event.TenantEvent;
import com.zjj.main.domain.role.RoleId;
import lombok.Data;
import org.jmolecules.event.annotation.DomainEvent;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年05月21日 14:03
 */
@Data
@DomainEvent
public class StockInRoleEvent extends TenantEvent {
    private RoleId id;
    private String name;
    private String code;
    private String roleSort;
    private Boolean enable;
    private Integer deleted;
}
