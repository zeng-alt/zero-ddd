package com.zjj.main.infrastructure.task;

import com.zjj.autoconfigure.component.tenant.Tenant;
import com.zjj.main.domain.role.event.InitRbacEvent;
import com.zjj.tenant.management.component.spi.TenantDataSourceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月07日 10:26
 */
@Component
public class RegisteredRbacTask implements ApplicationRunner {

    @Autowired
    private TenantDataSourceProvider tenantDataSourceService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.applicationEventPublisher.publishEvent(InitRbacEvent.of());
        for (Tenant tenant : this.tenantDataSourceService.findAll()) {
            this.applicationEventPublisher.publishEvent(InitRbacEvent.of(tenant.getTenantId(), tenant.getDb(), tenant.getSchema()));
        }
    }
}
