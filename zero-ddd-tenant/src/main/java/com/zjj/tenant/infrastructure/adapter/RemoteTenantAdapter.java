package com.zjj.tenant.infrastructure.adapter;

import com.zjj.autoconfigure.component.tenant.Tenant;
import com.zjj.exchange.tenant.api.RemoteTenantApi;
import com.zjj.tenant.infrastructure.db.entity.TenantDataSourceEntity;
import com.zjj.tenant.infrastructure.db.jpa.TenantDao;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.hexagonal.Adapter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年01月09日 20:45
 */
@Adapter
@Service
@RequiredArgsConstructor
public class RemoteTenantAdapter implements RemoteTenantApi {

    private final TenantDao tenantDao;

    @Override
    public Option<Tenant> findById(String tenantKey) {
        return tenantDao
                .findByTenantKey(tenantKey)
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
                });
    }

    @Override
    public List<Tenant> findAll() {
        return tenantDao.findAll()
                .stream()
                .filter(t -> Objects.nonNull(t.getTenantDataSource()))
                .filter(t -> Boolean.FALSE.equals(t.getTenantDataSource().getEnabled()))
                .filter(t -> t.getTenantDataSource().getEnabled())
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
