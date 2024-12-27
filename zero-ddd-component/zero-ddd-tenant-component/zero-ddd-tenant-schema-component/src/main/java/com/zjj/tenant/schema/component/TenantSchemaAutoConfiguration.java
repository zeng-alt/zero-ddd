package com.zjj.tenant.schema.component;

import com.zjj.autoconfigure.component.tenant.MultiTenancyProperties;
import com.zjj.tenant.management.component.service.TenantDataSourceService;
import com.zjj.tenant.management.component.service.TenantInitDataSourceService;
import com.zjj.tenant.management.component.spi.TenantSingleDataSourceProvider;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月23日 21:54
 */
@AutoConfiguration
public class TenantSchemaAutoConfiguration {

//    @Bean
//    public DynamicSchemaMultiTenantSpringLiquibase dynamicSchemaMultiTenantSpringLiquibase(RemoteTenantClient remoteTenantClient, LiquibaseProperties liquibaseProperties, ResourceLoader resourceLoader, TenantInitDataSourceService tenantInitDataSourceService, TenantDataSourceService tenantDataSourceService) {
//        return new DynamicSchemaMultiTenantSpringLiquibase(remoteTenantClient, liquibaseProperties, resourceLoader, tenantInitDataSourceService, tenantDataSourceService);
//    }

    @Bean
    @Lazy
    public MultiTenantConnectionProvider<String> schemaBasedMultiTenantConnectionProvider(
            ObjectProvider<DataSource> masterDataSource,
            MultiTenancyProperties tenancyProperties,
            ObjectProvider<TenantSingleDataSourceProvider> tenantSingleDataSourceProvider
    ) {
        return new SchemaBasedMultiTenantConnectionProvider(
                masterDataSource.getIfAvailable(),
                tenancyProperties,
                tenantSingleDataSourceProvider.getIfAvailable()
        );
    }

    @Bean
    @Lazy
    public TenantInitDataSourceService tenantSchemaInitService(JdbcTemplate jdbcTemplate, MultiTenancyProperties multiTenancyProperties) {
        return new TenantSchemaInitService(jdbcTemplate, multiTenancyProperties);
    }

    @Bean
    public HibernatePropertiesCustomizer schemaBasedHibernatePropertiesCustomizer(MultiTenantConnectionProvider<String> schemaBasedMultiTenantConnectionProvider) {
        return hibernateProperties -> {
            hibernateProperties.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, schemaBasedMultiTenantConnectionProvider);
        };
    }
}
