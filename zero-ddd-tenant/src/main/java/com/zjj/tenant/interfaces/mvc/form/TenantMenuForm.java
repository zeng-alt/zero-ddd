package com.zjj.tenant.interfaces.mvc.form;

import java.util.Collection;

public record TenantMenuForm(Long tenantId, Collection<Long> menuIds) {

}