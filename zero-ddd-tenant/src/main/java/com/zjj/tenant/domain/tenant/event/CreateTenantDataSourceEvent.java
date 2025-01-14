package com.zjj.tenant.domain.tenant.event;

import com.zjj.tenant.domain.tenant.cmd.StockInTenantDataSourceCmd;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.modulith.events.Externalized;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月01日 14:14
 */
@Getter
@Setter
@Externalized
public class CreateTenantDataSourceEvent {

    private String tenantKey;
    private Long tenantId;
    private String db;
    private String password;
    private String schema;
    private String mode;
    private Boolean enabled;

    public CreateTenantDataSourceEvent() {
    }

    public CreateTenantDataSourceEvent(String tenantKey, StockInTenantDataSourceCmd cmd) {
        this.tenantKey = tenantKey;
        BeanUtils.copyProperties(cmd, this);
    }
}
