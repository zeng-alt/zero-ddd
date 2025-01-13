package com.zjj.tenant.infrastructure.policy;

import com.zjj.bean.componenet.BeanHelper;
import com.zjj.tenant.domain.tenant.TenantRepository;
import com.zjj.tenant.domain.tenant.event.CreateTenantEvent;
import com.zjj.tenant.infrastructure.db.entity.TenantEntity;
import com.zjj.tenant.infrastructure.db.jpa.TenantDao;
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

    @ApplicationModuleListener
    public void on(CreateTenantEvent event) {
        TenantEntity tenantEntity = BeanHelper.copyToObject(event, TenantEntity.class);
        tenantDao.save(tenantEntity);
    }
}
