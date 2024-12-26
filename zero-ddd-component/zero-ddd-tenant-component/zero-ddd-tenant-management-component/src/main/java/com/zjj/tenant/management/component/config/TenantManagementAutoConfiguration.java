package com.zjj.tenant.management.component.config;

import com.zjj.cache.component.repository.impl.RedisTopicRepositoryImpl;
import com.zjj.exchange.tenant.client.RemoteTenantClient;
import com.zjj.exchange.tenant.domain.Tenant;
import com.zjj.tenant.management.component.service.TenantDataSourceService;
import com.zjj.tenant.management.component.service.TenantInitDataSourceService;
import com.zjj.tenant.management.component.service.TenantManagementService;
import com.zjj.tenant.management.component.service.TenantManagementServiceImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ResourceLoader;

import java.util.function.Consumer;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月23日 09:05
 */
@AutoConfiguration
public class TenantManagementAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public TenantManagementService tenantManagementService(
            LiquibaseProperties liquibaseProperties,
            ResourceLoader resourceLoader,
            TenantDataSourceService tenantDataSourceService,
            RedisTopicRepositoryImpl repository,
            TenantInitDataSourceService tenantInitDataSourceService
    ) {
        TenantManagementServiceImpl tenantManagementService = new TenantManagementServiceImpl(
                liquibaseProperties,
                resourceLoader,
                tenantDataSourceService,
                tenantInitDataSourceService);
        repository.addListener("tenant-channel", Tenant.class, new Consumer<Tenant>() {
            @Override
            public void accept(Tenant tenant) {
                tenantManagementService.createTenant(tenant);
            }
        });

        return tenantManagementService;
    }

    @Bean
    public DynamicDatasourceMultiTenantSpringLiquibase dynamicDatasourceMultiTenantSpringLiquibase(
            RemoteTenantClient remoteTenantClient,
            LiquibaseProperties liquibaseProperties,
            TenantInitDataSourceService tenantInitDataSourceService,
            TenantDataSourceService tenantDataSourceService,
            ResourceLoader resourceLoader
    ) {
        return new DynamicDatasourceMultiTenantSpringLiquibase(
                remoteTenantClient,
                liquibaseProperties,
                resourceLoader,
                tenantInitDataSourceService,
                tenantDataSourceService
        );
    }
}
