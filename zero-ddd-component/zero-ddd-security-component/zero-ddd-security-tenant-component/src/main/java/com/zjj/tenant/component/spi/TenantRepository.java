package com.zjj.tenant.component.spi;

import com.zjj.tenant.component.entity.ITenantEntity;

import java.util.List;
import java.util.Optional;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月24日 11:28
 */
public interface TenantRepository {

    List<ITenantEntity> queryAll();

    Optional<ITenantEntity> findByTenantKey(String tenantKey);
}
