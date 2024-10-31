package com.zjj.tenant.domain.tenant;

import com.zjj.tenant.domain.tenant.cmd.StockInTenantCmd;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月30日 21:29
 */
@Service
@RequiredArgsConstructor
public class TenantHandler {
    private final TenantRepository tenantRepository;
    private final TenantFactory tenantFactory;

    public void handler(StockInTenantCmd stockInTenantCmd) {
        ITenant tenant = this.tenantFactory.createTenant(stockInTenantCmd);
        this.tenantRepository.save(tenant);
    }
}
