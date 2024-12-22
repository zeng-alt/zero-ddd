package com.zjj.tenant.database.component.config;

import com.zjj.exchange.tenant.client.RemoteTenantClient;
import com.zjj.tenant.database.component.TenantDataBaseRoutingDatasource;
import com.zjj.tenant.database.component.config.tenant.hibernate.CurrentTenantIdentifierResolverImpl;
import com.zjj.tenant.database.component.config.tenant.hibernate.DynamicDataSourceBasedMultiTenantConnectionProvider;
import com.zjj.tenant.database.component.config.tenant.liquibase.DynamicDataSourceBasedMultiTenantSpringLiquibase;
import com.zjj.tenant.management.component.config.MultiTenancyProperties;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;

import javax.sql.DataSource;

/**
 * @author zengJiaJun
 * @crateTime 2024年12月21日 22:06
 * @version 1.0
 */
@AutoConfiguration
public class TenantAutoConfig {

    @Bean
    public DynamicDataSourceBasedMultiTenantSpringLiquibase dynamicDataSourceBasedMultiTenantSpringLiquibase(
            RemoteTenantClient remoteTenantClient,
            LiquibaseProperties liquibaseProperties,
            MultiTenancyProperties multiTenancyProperties,
            ResourceLoader resourceLoader
    ) {
        return new DynamicDataSourceBasedMultiTenantSpringLiquibase(remoteTenantClient, liquibaseProperties, multiTenancyProperties, resourceLoader);
    }

    @Primary
    @Bean
    public TenantDataBaseRoutingDatasource tenantDataSource(
            DataSource dataSource,
            DataSourceProperties dataSourceProperties,
            RemoteTenantClient remoteTenantClient,
            MultiTenancyProperties multiTenancyProperties
    ) {

        return new TenantDataBaseRoutingDatasource(
                dataSource,
                dataSourceProperties,
                remoteTenantClient,
                multiTenancyProperties,
                currentTenantIdentifierResolver()
        );
    }

    @Bean
    public CurrentTenantIdentifierResolver<String> currentTenantIdentifierResolver() {
        return new CurrentTenantIdentifierResolverImpl();
    }

    @Bean
    public MultiTenantConnectionProvider<String> multiTenantConnectionProvider(@Autowired @Qualifier("tenantDataSource") TenantDataBaseRoutingDatasource dataSource) {
        return new DynamicDataSourceBasedMultiTenantConnectionProvider(dataSource);
    }

    @Bean
    public HibernatePropertiesCustomizer tenantHibernatePropertiesCustomizer(MultiTenantConnectionProvider<String> multiTenantConnectionProvider, CurrentTenantIdentifierResolver<String> currentTenantIdentifierResolver) {
        return hibernateProperties -> {
            hibernateProperties.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
            hibernateProperties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolver);
        };
    }
}
