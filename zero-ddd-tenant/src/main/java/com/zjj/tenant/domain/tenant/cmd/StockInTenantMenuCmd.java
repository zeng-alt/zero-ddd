package com.zjj.tenant.domain.tenant.cmd;

import com.zjj.tenant.domain.tenant.TenantMenu;
import lombok.Builder;
import org.jmolecules.ddd.types.Association;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月01日 21:10
 */
@Builder
public record StockInTenantMenuCmd(
        Long tenantId,
        List<Association<TenantMenu, TenantMenu.TenantMenuId>> menuResources
) {

}
