package com.zjj.tenant.domain.tenant;

import io.vavr.control.Option;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月27日 21:44
 */
public interface TenantDataSourceRepository {

    public Option<TenantDataSource> findBySchema(String schema);

    public Option<TenantDataSource> findByDb(String db);

    Option<TenantDataSource> findById(Long id);


    TenantDataSource findTenantDataSourceById(Long id);

    void testDataSourceConnectionCmd(Long id);
}
