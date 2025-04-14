package com.zjj.main.infrastructure.task;

import com.zjj.autoconfigure.component.tenant.MultiTenancyProperties;
import com.zjj.autoconfigure.component.tenant.Tenant;
import com.zjj.autoconfigure.component.tenant.TenantContextHolder;
import com.zjj.main.domain.parameter.event.InitParameterEvent;
import com.zjj.tenant.management.component.spi.TenantDataSourceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月09日 15:53
 */
@Component
public class RegisteredParameterTask implements ApplicationRunner {

    @Autowired
    private TenantDataSourceProvider tenantDataSourceService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private MultiTenancyProperties multiTenancyProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        TenantContextHolder.setTenantId(multiTenancyProperties.getMaster());
        this.applicationEventPublisher.publishEvent(InitParameterEvent.of());
        for (Tenant tenant : this.tenantDataSourceService.findAll()) {
            this.applicationEventPublisher.publishEvent(InitParameterEvent.of(tenant.getTenantId(), tenant.getDb(), tenant.getSchema()));
        }
    }
}
