package com.zjj.tenant.domain.tenant.menu;

import io.vavr.control.Option;

import java.util.Collection;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月04日 13:43
 */
public interface TenantMenuRepository {

    public Option<TenantMenu> findById(Long id);

    public Void saveAll(Collection<TenantMenu> tenantMenuList);

    TenantMenu save(TenantMenu tenantMenu);
}
