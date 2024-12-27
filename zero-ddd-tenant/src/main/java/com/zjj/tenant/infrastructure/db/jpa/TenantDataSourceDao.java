package com.zjj.tenant.infrastructure.db.jpa;

import com.zjj.graphql.component.supper.BaseRepository;
import com.zjj.tenant.domain.tenant.TenantDataSource;
import io.vavr.control.Option;
import org.springframework.graphql.data.GraphQlRepository;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月30日 21:12
 */
@GraphQlRepository
public interface TenantDataSourceDao extends BaseRepository<TenantDataSource, Long> {

    Option<TenantDataSource> findBySchema(String schema);

    Option<TenantDataSource> findByDb(String db);
}
