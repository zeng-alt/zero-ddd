package com.zjj.main.domain.role.event;

import com.zjj.domain.component.event.TenantEvent;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月07日 10:13
 */
public class InitRbacEvent extends TenantEvent {

    public static InitRbacEvent of() {
        return new InitRbacEvent();
    }

    public static InitRbacEvent of(String tenant, String db, String schema) {
        InitRbacEvent initRbacEvent = new InitRbacEvent();
        initRbacEvent.set_database(db);
        initRbacEvent.set_tenant(tenant);
        initRbacEvent.set_schema(schema);
        return initRbacEvent;
    }
}
