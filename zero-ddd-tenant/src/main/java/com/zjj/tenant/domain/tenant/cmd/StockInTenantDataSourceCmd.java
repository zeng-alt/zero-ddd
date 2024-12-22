package com.zjj.tenant.domain.tenant.cmd;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月01日 21:59
 */
public record StockInTenantDataSourceCmd(
        Long tenantId,
        String poolName,
        String url,
        String username,
        String password,
        String schema,
        Boolean enabled
) {
}
