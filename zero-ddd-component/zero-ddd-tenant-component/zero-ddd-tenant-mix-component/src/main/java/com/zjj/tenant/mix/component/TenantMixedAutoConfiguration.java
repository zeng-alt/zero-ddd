package com.zjj.tenant.mix.component;

import com.zjj.autoconfigure.component.tenant.MultiTenancyProperties;
import com.zjj.tenant.database.component.DynamicDataSourceBasedMultiTenantConnectionProvider;
import com.zjj.tenant.database.component.TenantDataBaseRoutingDatasource;
import com.zjj.tenant.management.component.spi.TenantSingleDataSourceProvider;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@AutoConfiguration
public class TenantMixedAutoConfiguration {


    @Primary
    @Bean
    public TenantMixedRoutingDatasource tenantDataSource(
            DataSource dataSource,
            DataSourceProperties dataSourceProperties,
            TenantSingleDataSourceProvider tenantSingleDataSourceProvider,
            MultiTenancyProperties multiTenancyProperties,
            CurrentTenantIdentifierResolver<String> currentTenantIdentifierResolver
    ) {

        return new TenantMixedRoutingDatasource(
                dataSource,
                dataSourceProperties,
                tenantSingleDataSourceProvider,
                multiTenancyProperties,
                currentTenantIdentifierResolver
        );
    }



    @Bean
    public MultiTenantConnectionProvider<String> multiTenantConnectionProvider(@Autowired @Qualifier("tenantDataSource") TenantMixedRoutingDatasource dataSource) {
        return new DynamicDataSourceBasedMultiTenantConnectionProvider(dataSource);
    }

    @Bean
    public HibernatePropertiesCustomizer tenantDatabaseHibernatePropertiesCustomizer(MultiTenantConnectionProvider<String> multiTenantConnectionProvider) {
        return hibernateProperties -> {
            hibernateProperties.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
        };
    }

}