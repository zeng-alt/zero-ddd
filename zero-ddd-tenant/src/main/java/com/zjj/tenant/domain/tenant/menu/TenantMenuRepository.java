package com.zjj.tenant.domain.tenant.menu;

import java.util.Collection;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月04日 13:43
 */
public interface TenantMenuRepository {

    public Void saveAll(Collection<TenantMenu> tenantMenuList);
}
