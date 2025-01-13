package com.zjj.tenant.domain.tenant.event;

import com.zjj.tenant.domain.tenant.TenantDataSource;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月07日 21:27
 */
@Getter
@Setter
public class EnableTenantEvent extends TenantEvent {

    private String tenantKey;
    private Long tenantId;
    private String poolName;
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private String jndiName;
    private Boolean seata;
    private Boolean p6spy;
    private Boolean lazy;
    private String publicKey;
    private Boolean enable;

    private EnableTenantEvent(Object o, Long tenantId) {
        this.tenantId = tenantId;
    }

    public static EnableTenantEvent apply(Object o, Long tenantId, TenantDataSource tenantDataSource) {
        EnableTenantEvent enableTenantEvent = new EnableTenantEvent(o, tenantId);
        BeanUtils.copyProperties(tenantDataSource, enableTenantEvent);
        return enableTenantEvent;
    }
}
