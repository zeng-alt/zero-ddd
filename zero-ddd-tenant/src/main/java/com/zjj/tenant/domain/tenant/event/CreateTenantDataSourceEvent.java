package com.zjj.tenant.domain.tenant.event;

import com.zjj.autoconfigure.component.redis.RedisStringRepository;
import com.zjj.cache.component.repository.impl.RedisReliableTopicRepositoryImpl;
import com.zjj.domain.component.DomainEvent;
import com.zjj.tenant.domain.tenant.cmd.StockInTenantDataSourceCmd;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月01日 14:14
 */
@Getter
@Setter
public class CreateTenantDataSourceEvent extends TenantEvent {

    private String tenantKey;
    private Long tenantId;
    private String db;
    private String password;

    private String schema;
    private Boolean enabled;

    public CreateTenantDataSourceEvent(String tenantKey, StockInTenantDataSourceCmd cmd) {
        this.tenantKey = tenantKey;
        BeanUtils.copyProperties(cmd, this);
    }
}
