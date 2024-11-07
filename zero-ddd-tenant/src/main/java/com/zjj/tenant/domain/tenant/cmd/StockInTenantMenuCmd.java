package com.zjj.tenant.domain.tenant.cmd;

import com.zjj.tenant.domain.menu.MenuResource;
import com.zjj.tenant.domain.tenant.menu.TenantMenu;
import lombok.Builder;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月01日 15:10
 */
@Builder
public record StockInTenantMenuCmd(
        Long tenantId,
        List<TenantMenu> menuResources

) {

}
