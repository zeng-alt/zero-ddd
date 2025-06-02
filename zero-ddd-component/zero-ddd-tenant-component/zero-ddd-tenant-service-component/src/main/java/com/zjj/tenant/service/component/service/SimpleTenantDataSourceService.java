package com.zjj.tenant.service.component.service;

import com.zjj.autoconfigure.component.tenant.Tenant;
import com.zjj.tenant.management.component.spi.TenantDataSourceProvider;
import com.zjj.tenant.service.component.entity.TenantDataSourceEntity;
import com.zjj.tenant.service.component.repository.TenantRepository;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Objects;

/**
 * @author zengJiaJun
 * @crateTime 2025年03月16日 12:53
 * @version 1.0
 */
@RequiredArgsConstructor
public class SimpleTenantDataSourceService implements TenantDataSourceProvider {

    private final TenantRepository tenantRepository;

    @Override
    public Collection<Tenant> findAll() {
        return tenantRepository.findAll()
                .stream()
                .filter(t -> Objects.nonNull(t.getTenantDataSource()))
                .filter(t -> Boolean.FALSE.equals(t.getTenantDataSource().getEnabled()))
                .map(t -> {
                    TenantDataSourceEntity dataSource = t.getTenantDataSource();
                    return Tenant.builder()
                            .tenantId(t.getTenantKey())
                            .db(dataSource.getDb())
                            .password(dataSource.getPassword())
                            .schema(dataSource.getSchema())
                            .mode(dataSource.getMode())
                            .build();
                })
                .toList();
    }
}
