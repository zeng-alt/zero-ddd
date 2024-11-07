package com.zjj.tenant.domain.tenant.event;

import com.zjj.autoconfigure.component.redis.RedisStringRepository;
import com.zjj.cache.component.repository.impl.RedisReliableTopicRepositoryImpl;
import com.zjj.domain.component.DomainEvent;
import com.zjj.tenant.domain.tenant.cmd.StockInTenantDataSourceCmd;
import org.springframework.beans.BeanUtils;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月01日 14:14
 */
public class CreateTenantDataSourceEvent extends TenantEvent {

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

    public CreateTenantDataSourceEvent(Object o, String tenantKey, StockInTenantDataSourceCmd cmd) {
        super(o);
        this.tenantKey = tenantKey;
        BeanUtils.copyProperties(cmd, this);
    }
}
