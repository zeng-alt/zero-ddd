package com.zjj.tenant.infrastructure.policy;

import com.zjj.autoconfigure.component.tenant.Tenant;
import com.zjj.bean.componenet.ApplicationContextHelper;
import com.zjj.bean.componenet.BeanHelper;
import com.zjj.cache.component.repository.impl.RedisTopicRepositoryImpl;
import com.zjj.tenant.domain.tenant.event.CreateTenantDataSourceEvent;
import com.zjj.tenant.domain.tenant.event.CreateTenantEvent;
import com.zjj.tenant.infrastructure.db.entity.TenantDataSourceEntity;
import com.zjj.tenant.infrastructure.db.entity.TenantEntity;
import com.zjj.tenant.infrastructure.db.jpa.TenantDao;
import com.zjj.tenant.infrastructure.db.jpa.TenantDataSourceDao;
import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年01月13日 21:42
 */
@Component
@RequiredArgsConstructor
public class TenantPolicy {

    private final TenantDao tenantDao;
    private final TenantDataSourceDao tenantDataSourceDao;
    private final RedisTopicRepositoryImpl repository;

    @ApplicationModuleListener
    public void on(CreateTenantEvent event) {
        TenantEntity tenantEntity = BeanHelper.copyToObject(event, TenantEntity.class);
        tenantDao.save(tenantEntity);
    }

    @ApplicationModuleListener
    public void on1(CreateTenantDataSourceEvent createTenantDataSourceEvent) {}

    @ApplicationModuleListener
    public void on(CreateTenantDataSourceEvent createTenantDataSourceEvent) {
        TenantDataSourceEntity tenantDataSourceEntity = BeanHelper.copyToObject(createTenantDataSourceEvent, TenantDataSourceEntity.class);
        tenantDataSourceDao.save(tenantDataSourceEntity);

        Tenant tenant = new Tenant();
        tenant.setMode(Tenant.Mode.of(createTenantDataSourceEvent.getMode()));
        tenant.setTenantId(createTenantDataSourceEvent.getTenantKey());
        tenant.setDb(createTenantDataSourceEvent.getDb());
        tenant.setPassword(createTenantDataSourceEvent.getPassword());
        tenant.setSchema(createTenantDataSourceEvent.getSchema());
        ApplicationContextHelper.getBean(RedisTopicRepositoryImpl.class).publish("tenant-channel", tenant);
    }
}
