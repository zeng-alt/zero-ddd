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
public class FunctionAuthorizeEvent extends TenantEvent {

    private Set<Long> graphqlIds;
    private Long roleId;

    public static FunctionAuthorizeEvent of(Set<Long> graphqlIds, Long roleId) {
        FunctionAuthorizeEvent event = new FunctionAuthorizeEvent();
        event.setGraphqlIds(graphqlIds);
        event.setRoleId(roleId);
        return event;
    }
}
