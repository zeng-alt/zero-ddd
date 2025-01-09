package com.zjj.tenant.domain.tenant;

import io.vavr.control.Option;

import java.util.Collection;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月04日 13:43
 */
public interface TenantMenuRepository {

    public Option<TenantMenuAggregate> findById(Long id);

    public Void saveAll(Collection<TenantMenuAggregate> tenantMenuList);

    TenantMenuAggregate save(TenantMenuAggregate tenantMenu);
}
