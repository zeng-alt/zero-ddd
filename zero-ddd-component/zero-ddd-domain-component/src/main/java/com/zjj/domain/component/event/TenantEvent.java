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


    protected String tenant$;
    protected String database$;
    protected String schema$;

    protected TenantEvent() {
        this.tenant$ = TenantContextHolder.getTenantId();
        this.database$ = TenantContextHolder.getDatabase();
        this.schema$ = TenantContextHolder.getSchema();
    }


}
