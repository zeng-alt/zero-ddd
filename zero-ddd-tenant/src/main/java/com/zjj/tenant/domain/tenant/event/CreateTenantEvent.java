package com.zjj.tenant.domain.tenant.event;

import com.zjj.domain.component.event.TenantEvent;
import com.zjj.tenant.domain.tenant.Tenant;
import com.zjj.tenant.domain.tenant.cmd.StockInTenantCmd;
import com.zjj.tenant.domain.tenant.cmd.StockInTenantDataSourceCmd;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年01月13日 21:12
 */
@Getter
@Setter
public class CreateTenantEvent extends TenantEvent {

    private Long id;
    private String tenantKey;
    private String contactUsername;
    private String contactPhone;
    private String companyName;
    private String licenseNumber;
    private String address;
    private String domain;
    private String intro;
    private String remark;
    private LocalDateTime expireTime;
    private Long accountCount;


    public CreateTenantEvent() {
    }

    protected CreateTenantEvent(StockInTenantCmd cmd) {

        BeanUtils.copyProperties(cmd, this);
    }

    public static CreateTenantEvent apply(StockInTenantCmd cmd) {
        CreateTenantEvent createTenantEvent = new CreateTenantEvent();
        BeanUtils.copyProperties(cmd, createTenantEvent);
        return createTenantEvent;
    }

    public static CreateTenantEvent apply(Tenant tenant) {
        CreateTenantEvent createTenantEvent = new CreateTenantEvent();
        BeanUtils.copyProperties(tenant, createTenantEvent);
        createTenantEvent.id = tenant.getId() != null ? tenant.getId().getId() : null;
        return createTenantEvent;
    }


}
