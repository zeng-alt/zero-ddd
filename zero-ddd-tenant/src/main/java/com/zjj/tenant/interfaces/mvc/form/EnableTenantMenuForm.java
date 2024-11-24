package com.zjj.tenant.interfaces.mvc.form;

import com.zjj.tenant.domain.tenant.cmd.EnableTenantMenuCmd;
import io.github.linpeilie.annotations.AutoMapper;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月08日 21:45
 */
@AutoMapper(target = EnableTenantMenuCmd.class)
public record EnableTenantMenuForm(Long tenantId, Long menuId) {
}
