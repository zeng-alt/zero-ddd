package com.zjj.main.domain.role.event;

import com.zjj.domain.component.event.TenantEvent;
import lombok.Data;
import org.jmolecules.event.annotation.DomainEvent;

import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年05月23日 09:55
 */
@Data
@DomainEvent
public class ServiceAuthorizeEvent extends TenantEvent {
    private String service;
    private Long roleId;

    public static ServiceAuthorizeEvent of(String service, Long roleId) {
        ServiceAuthorizeEvent event = new ServiceAuthorizeEvent();
        event.service = service;
        event.roleId = roleId;
        return event;
    }
}
