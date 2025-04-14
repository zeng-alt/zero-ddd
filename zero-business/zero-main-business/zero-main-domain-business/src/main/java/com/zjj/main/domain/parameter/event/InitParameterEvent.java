package com.zjj.main.domain.parameter.event;

import com.zjj.domain.component.event.TenantEvent;
import com.zjj.main.domain.role.event.InitRbacEvent;
import org.jmolecules.event.annotation.DomainEvent;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月09日 15:50
 */
@DomainEvent
public class InitParameterEvent extends TenantEvent {

    public static InitParameterEvent of() {
        return new InitParameterEvent();
    }

    public static InitParameterEvent of(String tenant, String db, String schema) {
        InitParameterEvent initRbacEvent = new InitParameterEvent();
        initRbacEvent.set_database(db);
        initRbacEvent.set_tenant(tenant);
        initRbacEvent.set_schema(schema);
        return initRbacEvent;
    }
}
