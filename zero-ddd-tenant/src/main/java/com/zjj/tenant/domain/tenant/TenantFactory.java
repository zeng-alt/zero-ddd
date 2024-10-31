package com.zjj.tenant.domain.tenant;

import com.zjj.bean.componenet.BeanHelper;
import com.zjj.tenant.domain.tenant.cmd.StockInTenantCmd;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月30日 11:49
 */
@Component
@RequiredArgsConstructor
public class TenantFactory {
    private final TenantRepository tenantRepository;

    public ITenant createTenant(StockInTenantCmd stockInTenantCmd) {

        return (Tenant) tenantRepository
                .findByTenantKey(stockInTenantCmd::tenantKey)
                .map(tenant -> {
                    throw new IllegalArgumentException(stockInTenantCmd.tenantKey() + " 租户已存在");
                })
                .getOrElse(() -> BeanHelper.copyToObject(stockInTenantCmd, Tenant.class));
    }
}

