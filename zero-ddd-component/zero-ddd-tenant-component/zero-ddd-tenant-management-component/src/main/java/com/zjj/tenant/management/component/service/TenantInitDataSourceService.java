package com.zjj.tenant.management.component.service;


import com.zjj.autoconfigure.component.tenant.Tenant;

import javax.sql.DataSource;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月23日 21:21
 */
public interface TenantInitDataSourceService {
    void initDataSource(Tenant tenant, DataSource dataSource);
}
