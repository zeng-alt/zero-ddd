package com.zjj.tenant.Infrastructure.repository;

import com.zjj.tenant.Infrastructure.jpa.TenantDao;
import com.zjj.tenant.domain.tenant.ITenant;
import com.zjj.tenant.domain.tenant.Tenant;
import com.zjj.tenant.domain.tenant.TenantRepository;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月30日 11:13
 */
@Component
@RequiredArgsConstructor
public class TenantRepositoryImpl implements TenantRepository {

    private final TenantDao tenantDao;

    @Override
    public Option<ITenant> findByTenantKey(String tenantKey) {
        return tenantDao.findByTenantKey(tenantKey).map(t->t);
    }


    @Override
    public Tenant save(ITenant itenant) {
        if (itenant instanceof Tenant tenant) {
            return tenantDao.save(tenant);
        }
        throw new IllegalArgumentException("不能转换为 Tenant 类型");
    }
}
