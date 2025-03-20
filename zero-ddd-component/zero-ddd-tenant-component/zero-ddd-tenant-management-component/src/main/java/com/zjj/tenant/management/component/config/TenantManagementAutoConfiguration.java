package com.zjj.tenant.management.component.config;

import com.zjj.autoconfigure.component.redis.Lock;
import com.zjj.autoconfigure.component.tenant.Tenant;
import com.zjj.cache.component.repository.impl.RedisTopicRepositoryImpl;
import com.zjj.tenant.management.component.service.*;
import com.zjj.tenant.management.component.spi.TenantDataSourceProvider;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;

import java.util.function.Consumer;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月23日 21:05
 */
@AutoConfiguration
public class TenantManagementAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(TenantManagementService.class)
    public TenantManagementService tenantManagementService(
            LiquibaseProperties liquibaseProperties,
            ResourceLoader resourceLoader,
            TenantDataSourceService tenantDataSourceService,
            RedisTopicRepositoryImpl repository,
            ObjectProvider<Lock> lock
    ) {
        TenantManagementServiceImpl tenantManagementService = new TenantManagementServiceImpl(
                liquibaseProperties,
                resourceLoader,
                tenantDataSourceService,
                lock.getIfAvailable()
        );
        repository.addListener("tenant-channel", Tenant.class, new Consumer<Tenant>() {
            @Override
            public void accept(Tenant tenant) {
                tenantManagementService.createTenant(tenant);
            }
        });

        return tenantManagementService;
    }

    @Bean
    public DynamicMultiTenantDataSourceSpringLiquibaseConfiguration dynamicDatasourceMultiTenantSpringLiquibase(
            LiquibaseProperties liquibaseProperties,
            ResourceLoader resourceLoader,
            TenantDataSourceProvider tenantDataSourceProviders,
            TenantDataSourceService tenantDataSourceService
    ) {

        return new DynamicMultiTenantDataSourceSpringLiquibaseConfiguration(
                liquibaseProperties,
                resourceLoader,
                tenantDataSourceProviders,
                tenantDataSourceService
        );
    }
}
