package com.zjj.main.domain.rule.event;

import com.zjj.domain.component.event.TenantEvent;
import org.jmolecules.event.annotation.DomainEvent;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月10日 15:00
 */
@DomainEvent
public class InitPolicyRuleEvent extends TenantEvent {

    public static InitPolicyRuleEvent of() {
        return new InitPolicyRuleEvent();
    }

    public static InitPolicyRuleEvent of(String tenant, String db, String schema) {
        InitPolicyRuleEvent initPolicyRuleEvent = new InitPolicyRuleEvent();
        initPolicyRuleEvent.set_database(db);
        initPolicyRuleEvent.set_tenant(tenant);
        initPolicyRuleEvent.set_schema(schema);
        return initPolicyRuleEvent;
    }
}
