package com.zjj.tenant.domain.tenant.cmd;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月01日 21:59
 */
public record StockInTenantDataSourceCmd(
        Long tenantId,
        String poolName,
        String driverClassName,
        String url,
        String username,
        String password,
        String jndiName,
        Boolean seata,
        Boolean p6spy,
        Boolean lazy,
        String publicKey,
        Boolean enabled
) {
}
