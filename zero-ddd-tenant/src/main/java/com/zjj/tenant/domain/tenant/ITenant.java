package com.zjj.tenant.domain.tenant;

import com.zjj.tenant.domain.tenant.cmd.StockInTenantDataSourceCmd;
import com.zjj.tenant.domain.tenant.cmd.StockInTenantMenuCmd;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月30日 14:33
 */
public interface ITenant {

    ITenant save(StockInTenantDataSourceCmd sourceCmd);

    ITenant save(StockInTenantMenuCmd sourceCmd);

    ITenant disable();

    ITenant enable();

    ITenant disableMenu(Long menuId);

    ITenant enableMenu(Long menuId);

    TenantDataSource getTenantDataSource();
}
