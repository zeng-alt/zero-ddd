package com.zjj.autoconfigure.component.tenant;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月26日 21:31
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TenantContextImpl implements TenantContext {

    private String tenant;
    private String database;
    private String schema;

    public TenantContextImpl() {
    }

    public TenantContextImpl(String tenant) {
        this.tenant = tenant;
    }

    public TenantContextImpl(String tenant, String database, String schema) {
        this.tenant = tenant;
        this.database = database;
        this.schema = schema;
    }

}
