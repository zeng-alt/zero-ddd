package com.zjj.tenant.service.component.service;

import com.zjj.autoconfigure.component.tenant.Tenant;
import com.zjj.tenant.management.component.spi.TenantSingleDataSourceProvider;
import com.zjj.tenant.service.component.entity.TenantDataSourceEntity;
import com.zjj.tenant.service.component.repository.TenantRepository;
import io.vavr.control.Option;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

/**
 * @author zengJiaJun
 * @crateTime 2025年03月16日 12:53
 * @version 1.0
 */
@RequiredArgsConstructor
public class SimpleTenantSingleDataSourceService implements TenantSingleDataSourceProvider {

    private final TenantRepository tenantRepository;

    @Override
    public Option<Tenant> findById(String tenantKey) {
        return tenantRepository.findByTenantKey(tenantKey)
                .map(t -> {
                    TenantDataSourceEntity dataSource = t.getTenantDataSource();
                    return Tenant.builder()
                            .tenantId(t.getTenantKey())
                            .db(dataSource.getDb())
                            .password(dataSource.getPassword())
                            .schema(dataSource.getSchema())
                            .mode(dataSource.getMode())
                            .build();
                });
    }
}
