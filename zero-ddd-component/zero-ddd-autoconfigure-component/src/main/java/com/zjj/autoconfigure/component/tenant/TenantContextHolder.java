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
    private static final InheritableThreadLocal<String> currentDatabase = new InheritableThreadLocal<>();
    private static final InheritableThreadLocal<String> currentSchema = new InheritableThreadLocal<>();

    public static void setTenantId(String tenantId) {
        log.debug("Setting tenantId to " + tenantId);
        currentTenant.set(tenantId);
    }

    public static String getTenantId() {
        return currentTenant.get();
    }

    public static void setDatabase(String database) {
        log.debug("Setting database to " + database);
        currentDatabase.set(database);
    }

    public static String getDatabase() {
        return currentDatabase.get();
    }

    public static void setSchema(String schema) {
        log.debug("Setting schema to " + schema);
        currentSchema.set(schema);
    }

    public static String getSchema() {
        return currentSchema.get();
    }

    public static void clear(){
        currentTenant.remove();
        currentDatabase.remove();
        currentSchema.remove();
    }

    public static void switchTenant(String tenant) {
        setTenantId(tenant);
    }
}
