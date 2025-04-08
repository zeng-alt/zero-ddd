package com.zjj.domain.component.event;

import com.zjj.autoconfigure.component.tenant.TenantContextHolder;
import lombok.Getter;
import lombok.Setter;
import org.jmolecules.event.types.DomainEvent;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年01月14日 21:38
 */
@Getter
@Setter
public abstract class TenantEvent implements DomainEvent {


    protected String _tenant;
    protected String _database;
    protected String _schema;

    protected TenantEvent() {
        this._tenant = TenantContextHolder.getTenantId();
        this._database = TenantContextHolder.getDatabase();
        this._schema = TenantContextHolder.getSchema();
    }


}
