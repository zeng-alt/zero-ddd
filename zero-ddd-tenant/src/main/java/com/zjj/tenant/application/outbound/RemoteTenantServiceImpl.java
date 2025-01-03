package com.zjj.tenant.application.outbound;

import com.zjj.autoconfigure.component.tenant.Tenant;
import com.zjj.exchange.tenant.api.RemoteTenantApi;
import com.zjj.tenant.infrastructure.db.jpa.TenantDao;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zengJiaJun
 * @crateTime 2024年12月21日 22:31
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class RemoteTenantServiceImpl implements RemoteTenantApi {

    private final TenantDao tenantDao;

    @Override
    public Option<Tenant> findById(String id) {
        return tenantDao
                .findByTenantKey(id)
                .filter(t -> t.getTenantDataSource() != null)
                .map(tenant -> Tenant.builder()
                        .tenantId(tenant.getTenantKey())
                        .schema(tenant.getTenantDataSource().getSchema())
                        .db(tenant.getTenantDataSource().getDb())
                        .password(tenant.getTenantDataSource().getPassword())
                        .build()
                );
    }

    @Override
    public List<Tenant> findAll() {
        return tenantDao.findAll()
                .stream()
                .filter(t -> t.getTenantDataSource() != null)
                .map(tenant -> Tenant.builder()
                        .tenantId(tenant.getTenantKey())
                        .schema(tenant.getTenantDataSource().getSchema())
                        .db(tenant.getTenantDataSource().getDb())
                        .password(tenant.getTenantDataSource().getPassword())
                        .build()
                ).toList();
    }
}
