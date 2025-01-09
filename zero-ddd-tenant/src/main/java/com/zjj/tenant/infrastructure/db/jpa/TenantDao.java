package com.zjj.tenant.infrastructure.db.jpa;

import com.zjj.graphql.component.supper.BaseRepository;
import com.zjj.tenant.infrastructure.db.entity.TenantEntity;
import io.vavr.control.Option;
import org.springframework.graphql.data.GraphQlRepository;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月30日 21:09
 */
@GraphQlRepository
public interface TenantDao extends BaseRepository<TenantEntity, Long> {

    Option<TenantEntity> findById(Long id);

    TenantEntity save(TenantEntity tenant);

    Option<TenantEntity> findByTenantKey(String tenantKey);

    List<TenantEntity> findAll();
}
