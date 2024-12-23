package com.zjj.tenant.schema.component;

import com.zjj.autoconfigure.component.tenant.MultiTenancyProperties;
import com.zjj.exchange.tenant.client.RemoteTenantClient;
import com.zjj.tenant.management.component.service.TenantInitDataSourceService;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月23日 21:54
 */
@AutoConfiguration
public class TenantSchemaAutoConfiguration {


    @Bean
    public MultiTenantConnectionProvider<String> schemaBasedMultiTenantConnectionProvider(DataSource datasource, MultiTenancyProperties tenancyProperties, RemoteTenantClient remoteTenantClient) {
        return new SchemaBasedMultiTenantConnectionProvider(datasource, tenancyProperties, remoteTenantClient);
    }

    @Bean
    public TenantInitDataSourceService tenantSchemaInitService(JdbcTemplate jdbcTemplate, MultiTenancyProperties multiTenancyProperties) {
        return new TenantSchemaInitService(jdbcTemplate, multiTenancyProperties);
    }
}
