package com.zjj.main.domain.rule.event;

import com.zjj.domain.component.event.TenantEvent;
import lombok.Data;
import org.jmolecules.event.annotation.DomainEvent;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月16日 09:56
 */
@Data
@DomainEvent
public class StockInPolicyRuleEvent extends TenantEvent {
    private Long id;
    private Boolean preAuth;
    private String condition;
    private Boolean enable;
    private Long permissionId;
}
