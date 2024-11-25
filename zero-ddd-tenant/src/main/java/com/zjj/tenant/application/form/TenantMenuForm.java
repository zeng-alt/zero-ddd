package com.zjj.tenant.application.form;

import java.util.Collection;

public record TenantMenuForm(Long tenantId, Collection<Long> menuIds) {

}