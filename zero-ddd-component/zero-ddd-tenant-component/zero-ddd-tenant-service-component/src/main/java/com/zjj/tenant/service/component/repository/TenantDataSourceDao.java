package com.zjj.tenant.service.component.repository;


import com.zjj.tenant.service.component.entity.TenantDataSourceEntity;
import io.vavr.control.Option;
import org.springframework.data.repository.Repository;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月30日 21:12
 */
@org.springframework.stereotype.Repository
public interface TenantDataSourceDao extends Repository<TenantDataSourceEntity, Long> {

    Option<TenantDataSourceEntity> findBySchema(String schema);

    Option<TenantDataSourceEntity> findByDb(String db);

    TenantDataSourceEntity save(TenantDataSourceEntity tenantDataSourceEntity);
}
