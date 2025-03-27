package com.zjj.tenant.infrastructure.db.jpa;

import com.zjj.domain.component.BaseRepository;
import com.zjj.tenant.infrastructure.db.entity.TenantDataSourceEntity;
import io.vavr.control.Option;
import org.springframework.graphql.data.GraphQlRepository;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月30日 21:12
 */
@GraphQlRepository
public interface TenantDataSourceDao extends BaseRepository<TenantDataSourceEntity, Long> {

    Option<TenantDataSourceEntity> findBySchema(String schema);

    Option<TenantDataSourceEntity> findByDb(String db);

    TenantDataSourceEntity save(TenantDataSourceEntity tenantDataSourceEntity);
}
