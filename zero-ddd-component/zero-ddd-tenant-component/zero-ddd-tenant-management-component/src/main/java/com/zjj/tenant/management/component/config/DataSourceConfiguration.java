package com.zjj.tenant.management.component.config;

import com.zaxxer.hikari.HikariDataSource;
import com.zjj.exchange.tenant.client.RemoteTenantClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月20日 21:14
 */
@RequiredArgsConstructor
@AutoConfiguration
public class DataSourceConfiguration {

    private final DataSourceProperties dataSourceProperties;

    /**
     *
     * @param remoteTenantClient 这个参数不能删除，防止多租户模式下，remoteTenantClient在EntityManagerFactory之后才加载
     * @return
     */
    @Bean
    @LiquibaseDataSource
    public DataSource masterDataSource(RemoteTenantClient remoteTenantClient) {
        HikariDataSource dataSource = dataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();

        dataSource.setPoolName("masterDataSource");
        return dataSource;
    }
}
