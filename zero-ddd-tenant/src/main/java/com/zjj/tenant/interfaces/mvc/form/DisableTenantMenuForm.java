package com.zjj.tenant.interfaces.mvc.form;


import com.zjj.tenant.domain.tenant.cmd.DisableTenantMenuCmd;
import io.github.linpeilie.annotations.AutoMapper;

@AutoMapper(target = DisableTenantMenuCmd.class)
public record DisableTenantMenuForm(Long tenantId, Long menuId) {
}