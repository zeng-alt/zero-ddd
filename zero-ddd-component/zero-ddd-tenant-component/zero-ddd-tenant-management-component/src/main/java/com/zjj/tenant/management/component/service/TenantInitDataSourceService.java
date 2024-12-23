package com.zjj.tenant.management.component.service;

import com.zjj.exchange.tenant.domain.Tenant;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月23日 21:21
 */
public interface TenantInitDataSourceService {
    void initDataSource(Tenant tenant);
}
