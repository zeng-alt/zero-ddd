package com.zjj.tenant.management.component.config;

import com.zaxxer.hikari.HikariDataSource;
import com.zjj.exchange.tenant.client.RemoteTenantClient;
import com.zjj.tenant.management.component.spi.DefaultTenantDataSourceProvider;
import com.zjj.tenant.management.component.spi.DefaultTenantSingleDataSourceProvider;
import com.zjj.tenant.management.component.spi.TenantDataSourceProvider;
import com.zjj.tenant.management.component.spi.TenantSingleDataSourceProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import javax.sql.DataSource;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月20日 21:14
 */
@RequiredArgsConstructor
public class DataSourceConfiguration {

    private final DataSourceProperties dataSourceProperties;

    /**
     *
     * @param remoteTenantClient 这个参数不能删除，防止多租户模式下，remoteTenantClient在EntityManagerFactory之后才加载
     * @return
     */
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
    @Lazy
    @ConditionalOnMissingBean(TenantSingleDataSourceProvider.class)
    public TenantSingleDataSourceProvider tenantSingleDataSourceProvider(ObjectProvider<RemoteTenantClient> remoteTenantClient) {
        return new DefaultTenantSingleDataSourceProvider(remoteTenantClient);
    }

    @Bean
    @Lazy
    public TenantDataSourceProvider defaultTenantDataSourceProvider(ObjectProvider<RemoteTenantClient> remoteTenantClient) {
        return new DefaultTenantDataSourceProvider(remoteTenantClient);
    }
}
