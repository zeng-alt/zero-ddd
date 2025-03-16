package com.zjj.tenant.service.component.repository;



import com.zjj.tenant.service.component.entity.TenantEntity;
import io.vavr.control.Option;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月30日 21:09
 */
@Repository
public interface TenantRepository extends org.springframework.data.repository.Repository<TenantEntity, Long> {

    Option<TenantEntity> findById(Long id);

    TenantEntity save(TenantEntity tenant);

    Option<TenantEntity> findByTenantKey(String tenantKey);

    List<TenantEntity> findAll();
}
