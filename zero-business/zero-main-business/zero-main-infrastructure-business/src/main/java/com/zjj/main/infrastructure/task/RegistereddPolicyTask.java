package com.zjj.main.infrastructure.task;

import com.zjj.autoconfigure.component.tenant.MultiTenancyProperties;
import com.zjj.autoconfigure.component.tenant.Tenant;
import com.zjj.autoconfigure.component.tenant.TenantContextHolder;
import com.zjj.main.domain.parameter.event.InitParameterEvent;
import com.zjj.main.domain.rule.event.InitPolicyRuleEvent;
import com.zjj.tenant.management.component.spi.TenantDataSourceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月10日 14:38
 */
@Component
public class RegistereddPolicyTask implements ApplicationRunner {

    @Autowired
    private TenantDataSourceProvider tenantDataSourceService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private MultiTenancyProperties multiTenancyProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        TenantContextHolder.setTenantId(multiTenancyProperties.getMaster());
        this.applicationEventPublisher.publishEvent(InitPolicyRuleEvent.of());
        for (Tenant tenant : this.tenantDataSourceService.findAll()) {
            this.applicationEventPublisher.publishEvent(InitPolicyRuleEvent.of(tenant.getTenantId(), tenant.getDb(), tenant.getSchema()));
        }
    }
}
