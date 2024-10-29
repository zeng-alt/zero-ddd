package com.zjj.tenant.component.supper;

import com.zjj.tenant.component.spi.DynamicSourceManage;
import com.zjj.tenant.component.spi.TenantContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月25日 21:30
 */
@RequiredArgsConstructor
public class DefaultTenantContextHolder implements TenantContextHolder {

    private final DynamicSourceManage dynamicSourceManage;
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();


    @Override
    public void clearTenant() {
        contextHolder.remove();
    }

    @Override
    public Set<String> getTenants() {
        return dynamicSourceManage.getAllDs();
    }

    @Nullable
    @Override
    public String getCurrentTenant() {
        String db = contextHolder.get();
        return StringUtils.hasText(db) ? db : dynamicSourceManage.getPrimary();
    }

    @Override
    public void setCurrentTenant(String tenant) {
        contextHolder.set(tenant);
    }

}
