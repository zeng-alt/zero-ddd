package com.zjj.tenant.management.component.service;

import com.zjj.exchange.tenant.domain.Tenant;
import org.springframework.orm.jpa.vendor.Database;

/**
 * @author zengJiaJun
 * @crateTime 2024年12月22日 16:29
 * @version 1.0
 */
public interface TenantManagementService {


    public void createTenant(Tenant tenant);
}
