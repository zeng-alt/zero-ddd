package com.zjj.tenant.domain.tenant;

import org.jmolecules.ddd.types.AggregateRoot;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年01月09日 19:40
 */
public interface TenantMenuAggregate extends AggregateRoot<TenantMenu, TenantMenu.TenantMenuId> {

    public TenantMenu disable();

    public TenantMenu enable();
}
