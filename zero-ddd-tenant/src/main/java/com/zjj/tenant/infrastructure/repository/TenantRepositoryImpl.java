package com.zjj.tenant.infrastructure.repository;

import com.zjj.autoconfigure.component.tenant.TenantMode;
import com.zjj.bean.componenet.BeanHelper;
import com.zjj.domain.component.DomainBeanHelper;
import com.zjj.tenant.domain.tenant.*;
import com.zjj.tenant.infrastructure.db.entity.TenantEntity;
import com.zjj.tenant.infrastructure.db.jpa.TenantDao;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
    public Option<TenantAggregate> findByTenantKey(String tenantKey) {
        return tenantDao.findByTenantKey(tenantKey).map(t -> DomainBeanHelper.copyToDomain(t, Tenant.class, TenantId.class));
    }

    @Override
    public Option<TenantAggregate> findById(Long id) {
        return tenantDao
                .findById(id)
                .map(t -> {
                    Tenant tenant = DomainBeanHelper.copyToDomain(t, Tenant.class, TenantId.class);
                    if (t.getTenantDataSource() != null) {
                        TenantDataSource tenantDataSource = BeanHelper.copyToObject(t.getTenantDataSource(), TenantDataSource.class);
                        tenantDataSource.setMode(TenantMode.valueOf(t.getTenantDataSource().getMode()));
                        tenant.setTenantDataSource(tenantDataSource);
                    }
                    return tenant;
                });
    }


    @Override
    public TenantAggregate save(TenantAggregate tenantAggregate) {
        if (tenantAggregate instanceof Tenant tenant) {
            TenantEntity tenantEntity = DomainBeanHelper.copyToDomain(tenant, TenantEntity.class);
            tenantDao.save(tenantEntity);
            return BeanHelper.copyToObject(tenantEntity, Tenant.class);
        }
        throw new IllegalArgumentException("不能转换为 Tenant 类型");
    }
}
