package com.zjj.main.domain.role.event;

import com.zjj.domain.component.event.TenantEvent;
import lombok.Data;
import org.jmolecules.event.annotation.DomainEvent;

import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年05月23日 09:56
 */
@Data
@DomainEvent
public class FunctionCancelAuthorizeEvent extends TenantEvent {

    private Set<Long> graphqlIds;
    private Long roleId;

    public static FunctionCancelAuthorizeEvent of(Set<Long> graphqlIds, Long roleId) {
        FunctionCancelAuthorizeEvent event = new FunctionCancelAuthorizeEvent();
        event.graphqlIds = graphqlIds;
        event.roleId = roleId;
        return event;
    }
}
