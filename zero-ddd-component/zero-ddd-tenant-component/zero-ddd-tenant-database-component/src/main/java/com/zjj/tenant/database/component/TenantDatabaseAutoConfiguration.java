package com.zjj.tenant.database.component;


import com.zjj.autoconfigure.component.tenant.MultiTenancyProperties;
import com.zjj.tenant.management.component.service.TenantInitDataSourceService;
import com.zjj.tenant.management.component.spi.TenantSingleDataSourceProvider;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

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
    public TenantDataBaseRoutingDatasource tenantDataSource(
            @Qualifier("masterDataSource") DataSource dataSource,
            DataSourceProperties dataSourceProperties,
            ConfigurableListableBeanFactory beanFactory,
//            TenantSingleDataSourceProvider tenantSingleDataSourceProvider,
            MultiTenancyProperties multiTenancyProperties,
            CurrentTenantIdentifierResolver<String> currentTenantIdentifierResolver,
            TenantInitDataSourceService tenantDatabaseInitService
    ) {

        return new TenantDataBaseRoutingDatasource(
                dataSource,
                dataSourceProperties,
                beanFactory,
//                tenantSingleDataSourceProvider,
                multiTenancyProperties,
                currentTenantIdentifierResolver,
                tenantDatabaseInitService
        );

    }

    @Bean
    public MultiTenantConnectionProvider<String> multiTenantConnectionProvider(@Autowired @Qualifier("tenantDataSource") TenantDataBaseRoutingDatasource dataSource) {
        return new DynamicDataSourceBasedMultiTenantConnectionProvider(dataSource);
    }

    @Bean
    public HibernatePropertiesCustomizer tenantDatabaseHibernatePropertiesCustomizer(MultiTenantConnectionProvider<String> multiTenantConnectionProvider) {
        return hibernateProperties -> {
            hibernateProperties.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
        };
    }

    @Bean
    public TenantInitDataSourceService tenantInitDataSourceService(MultiTenancyProperties multiTenancyProperties) {
        return new TenantDatabaseInitService(multiTenancyProperties);
    }
}
