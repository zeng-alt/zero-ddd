package com.zjj.tenant.management.component.service;

import com.zjj.exchange.tenant.domain.Tenant;

import javax.sql.DataSource;

/**
 * @author zengJiaJun
 * @crateTime 2024年12月22日 16:27
 * @version 1.0
 */
public interface TenantDatabaseService {


    public void addTenantDatabase(String tenant, DataSource dataSource);
}
