package com.zjj.tenant.mix.component;

import com.zjj.autoconfigure.component.tenant.MultiTenancyProperties;
import com.zjj.core.component.crypto.EncryptionService;
import com.zjj.tenant.database.component.proovider.DynamicMultiTenantDataSourceConnectionProvider;
import org.hibernate.cfg.MultiTenancySettings;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@AutoConfiguration
public class TenantMixedAutoConfiguration {


    @Primary
    @Bean
    @ConditionalOnBean(name = {"masterDataSource"})
    public TenantMixedDataSourceRoutingDatasource tenantDataSource(
            @Qualifier("masterDataSource") DataSource dataSource,
            DataSourceProperties dataSourceProperties,
            ConfigurableListableBeanFactory beanFactory,
            MultiTenancyProperties multiTenancyProperties,
            EncryptionService encryptionService
    ) {

        return new TenantMixedDataSourceRoutingDatasource(
                dataSource,
                dataSourceProperties,
                beanFactory,
                multiTenancyProperties,
                encryptionService
        );
    }



    @Bean
    public MultiTenantConnectionProvider<String> multiTenantConnectionProvider(@Autowired @Qualifier("tenantDataSource") TenantMixedDataSourceRoutingDatasource dataSource) {
        return new DynamicMultiTenantDataSourceConnectionProvider<>(dataSource);
    }

    @Bean
    public HibernatePropertiesCustomizer tenantDatabaseHibernatePropertiesCustomizer(MultiTenantConnectionProvider<String> multiTenantConnectionProvider) {
        return hibernateProperties -> hibernateProperties.put(MultiTenancySettings.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
    }

}