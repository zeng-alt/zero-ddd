package com.zjj.tenant.management.component.config;

import com.zaxxer.hikari.HikariDataSource;
import com.zjj.autoconfigure.component.redis.RedisSubPubRepository;
import com.zjj.cache.component.repository.impl.RedisTopicRepositoryImpl;
import com.zjj.exchange.tenant.domain.Tenant;
import com.zjj.tenant.management.component.service.TenantDatabaseService;
import com.zjj.tenant.management.component.service.TenantManagementService;
import com.zjj.tenant.management.component.service.TenantManagementServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.function.Consumer;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月20日 21:14
 */
@RequiredArgsConstructor
@AutoConfiguration
@EnableConfigurationProperties(MultiTenancyProperties.class)
public class DataSourceConfiguration {

    private final DataSourceProperties dataSourceProperties;

    @Bean
    @LiquibaseDataSource
    public DataSource masterDataSource() {
        HikariDataSource dataSource = dataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
        dataSource.setPoolName("masterDataSource");
        return dataSource;
    }

    @Bean
    @ConditionalOnMissingBean
    public TenantManagementService tenantManagementService(
            MultiTenancyProperties multiTenancyProperties,
            JdbcTemplate jdbcTemplate,
            LiquibaseProperties liquibaseProperties,
            ResourceLoader resourceLoader,
            TenantDatabaseService tenantDatabaseService,
            RedisTopicRepositoryImpl repository,
            DataSourceProperties dataSourceProperties
    ) {
        TenantManagementServiceImpl tenantManagementService = new TenantManagementServiceImpl(multiTenancyProperties, jdbcTemplate, liquibaseProperties, resourceLoader, tenantDatabaseService, dataSourceProperties);
        repository.addListener("tenant-channel", Tenant.class, new Consumer<Tenant>() {
            @Override
            public void accept(Tenant tenant) {
                tenantManagementService.createTenant(tenant.getTenantId(), tenant.getDb(), tenant.getPassword());
            }
        });

        return tenantManagementService;
    }


}
