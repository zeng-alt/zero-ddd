package com.zjj.tenant.component.repository;

import com.zjj.tenant.component.entity.ITenantEntity;
import com.zjj.tenant.component.entity.TenantEntity;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月28日 21:30
 */
public interface DefaultTenantRepository extends Repository<TenantEntity, Long> {

    List<ITenantEntity> findAll();
}
