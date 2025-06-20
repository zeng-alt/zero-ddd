package com.zjj.tenant.database.component.configuration;


import com.zjj.autoconfigure.component.tenant.MultiTenancyProperties;
import com.zjj.core.component.crypto.EncryptionService;
import com.zjj.tenant.database.component.proovider.DynamicMultiTenantDataSourceConnectionProvider;
import com.zjj.tenant.database.component.TenantDataSourceRoutingDatasource;
import com.zjj.tenant.database.component.TenantDatabaseInitService;
import com.zjj.tenant.management.component.service.TenantInitDataSourceService;
import org.hibernate.cfg.MultiTenancySettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author zengJiaJun
 * @crateTime 2024年12月21日 22:06
 * @version 1.0
 */
@AutoConfiguration
public class TenantDatabaseAutoConfiguration {

    @Primary
    @Bean
    public TenantDataSourceRoutingDatasource tenantDataSource(
            @Qualifier("masterDataSource") DataSource dataSource,
            DataSourceProperties dataSourceProperties,
            ConfigurableListableBeanFactory beanFactory,
            MultiTenancyProperties multiTenancyProperties,
            CurrentTenantIdentifierResolver<String> currentTenantIdentifierResolver,
            TenantInitDataSourceService tenantDatabaseInitService,
            EncryptionService encryptionService
    ) {

        return new TenantDataSourceRoutingDatasource(
                dataSource,
                dataSourceProperties,
                beanFactory,
                multiTenancyProperties,
                currentTenantIdentifierResolver,
                tenantDatabaseInitService,
                encryptionService
        );

    }

    @Bean
    public MultiTenantConnectionProvider<String> multiTenantConnectionProvider(@Autowired @Qualifier("tenantDataSource") TenantDataSourceRoutingDatasource dataSource) {
        return new DynamicMultiTenantDataSourceConnectionProvider<>(dataSource);
    }

    @Bean
    public HibernatePropertiesCustomizer tenantDatabaseHibernatePropertiesCustomizer(MultiTenantConnectionProvider<String> multiTenantConnectionProvider) {
        return hibernateProperties -> hibernateProperties.put(MultiTenancySettings.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
    }

    @Bean
    public TenantInitDataSourceService tenantInitDataSourceService(MultiTenancyProperties multiTenancyProperties) {
        return new TenantDatabaseInitService(multiTenancyProperties);
    }
}
