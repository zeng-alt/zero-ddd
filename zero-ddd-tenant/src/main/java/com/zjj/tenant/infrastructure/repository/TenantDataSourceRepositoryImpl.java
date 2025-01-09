package com.zjj.tenant.infrastructure.repository;

import com.zjj.domain.component.DomainBeanHelper;
import com.zjj.tenant.domain.tenant.TenantDataSource;
import com.zjj.tenant.domain.tenant.TenantDataSourceRepository;
import com.zjj.tenant.infrastructure.db.jpa.TenantDataSourceDao;
import io.vavr.control.Option;
import org.springframework.stereotype.Component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月27日 21:50
 */
@Component
public record TenantDataSourceRepositoryImpl(TenantDataSourceDao tenantDataSourceDao) implements TenantDataSourceRepository {


    @Override
    public Option<TenantDataSource> findBySchema(String schema) {
        return tenantDataSourceDao.findBySchema(schema).map(t -> DomainBeanHelper.copyToDomain(t, TenantDataSource.class, TenantDataSource.TenantDataSourceId.class));
    }

    @Override
    public Option<TenantDataSource> findByDb(String db) {
        return tenantDataSourceDao.findByDb(db).map(t -> DomainBeanHelper.copyToDomain(t, TenantDataSource.class, TenantDataSource.TenantDataSourceId.class));
    }
}
