package com.zjj.tenant.domain.tenant;

import com.zjj.tenant.domain.tenant.cmd.StockInTenantDataSourceCmd;
import com.zjj.tenant.domain.tenant.cmd.StockInTenantMenuCmd;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月30日 14:33
 */
public interface TenantAggregate {

    TenantAggregate save(StockInTenantDataSourceCmd sourceCmd);

    TenantAggregate save(StockInTenantMenuCmd sourceCmd);

    TenantAggregate disable();

    TenantAggregate enable();

    TenantAggregate disableMenu(Long menuId);

    TenantAggregate enableMenu(Long menuId);

    TenantDataSource getTenantDataSource();
}
