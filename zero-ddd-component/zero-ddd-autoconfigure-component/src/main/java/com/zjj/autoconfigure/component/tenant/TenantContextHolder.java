package com.zjj.autoconfigure.component.tenant;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月03日 21:09
 */
@Slf4j
public final class TenantContextHolder {

    private TenantContextHolder() {}

    private static final InheritableThreadLocal<String> currentTenant = new InheritableThreadLocal<>();

    public static void setTenantId(String tenantId) {
        log.debug("Setting tenantId to " + tenantId);
        currentTenant.set(tenantId);
    }

    public static String getTenantId() {
        return currentTenant.get();
    }

    public static void clear(){
        currentTenant.remove();
    }

    public static void switchPrimaryTenant() {
        currentTenant.set("master");
    }

    public static void switchTenant(String tenant) {
        setTenantId(tenant);
    }

    public static String getPrimaryTenant() {
        return "master";
    }
}
