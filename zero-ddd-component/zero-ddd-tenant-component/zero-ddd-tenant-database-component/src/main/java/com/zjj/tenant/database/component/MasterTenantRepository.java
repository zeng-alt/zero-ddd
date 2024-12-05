package com.zjj.tenant.database.component;

import java.util.Optional;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月04日 11:28
 */
public interface MasterTenantRepository {
    Optional<TenantDatabase> findByTenantId(String key);
}
