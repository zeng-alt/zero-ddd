package com.zjj.tenant.service.component.service;

import com.zjj.autoconfigure.component.tenant.Tenant;
import com.zjj.tenant.management.component.spi.TenantSingleDataSourceProvider;
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
    public Option<Tenant> findById(String id) {
        return Option.none();
    }
}
