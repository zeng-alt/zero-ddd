package com.zjj.tenant.service.component.service;

import com.zjj.autoconfigure.component.tenant.Tenant;
import com.zjj.tenant.management.component.spi.TenantDataSourceProvider;
import com.zjj.tenant.service.component.repository.TenantRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

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
//        return tenantRepository
//                .findAll()
//                .stream()
//                .map()
//                .collect(Collectors.toList());

        return Collections.emptyList();
    }
}
