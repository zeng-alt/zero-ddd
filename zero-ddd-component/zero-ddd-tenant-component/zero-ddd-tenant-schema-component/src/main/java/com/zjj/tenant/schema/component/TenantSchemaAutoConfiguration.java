package com.zjj.tenant.schema.component;

import com.zjj.autoconfigure.component.tenant.MultiTenancyProperties;
import com.zjj.core.component.crypto.EncryptionService;
import com.zjj.tenant.management.component.service.TenantInitDataSourceService;
import com.zjj.tenant.management.component.spi.TenantSingleDataSourceProvider;
import org.hibernate.cfg.MultiTenancySettings;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import javax.sql.DataSource;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月23日 21:54
 */
@AutoConfiguration
public class TenantSchemaAutoConfiguration {

    @Bean
    @Lazy
    public MultiTenantConnectionProvider<String> schemaBasedMultiTenantConnectionProvider(
            ObjectProvider<DataSource> masterDataSource,
            MultiTenancyProperties tenancyProperties,
            ObjectProvider<TenantSingleDataSourceProvider> tenantSingleDataSourceProvider,
            TenantInitDataSourceService tenantInitDataSourceService,
            EncryptionService encryptionService
    ) {
        return new TenantSchemaMultiTenantConnectionProvider(
                masterDataSource.getIfAvailable(),
                tenancyProperties,
                tenantSingleDataSourceProvider.getIfAvailable(),
                tenantInitDataSourceService,
                encryptionService
        );
    }

    @Bean
    @Lazy
    public TenantInitDataSourceService tenantSchemaInitService(MultiTenancyProperties multiTenancyProperties) {
        return new TenantSchemaInitService(multiTenancyProperties);
    }

    @Bean
    public HibernatePropertiesCustomizer schemaBasedHibernatePropertiesCustomizer(MultiTenantConnectionProvider<String> schemaBasedMultiTenantConnectionProvider) {
        return hibernateProperties -> hibernateProperties.put(MultiTenancySettings.MULTI_TENANT_CONNECTION_PROVIDER, schemaBasedMultiTenantConnectionProvider);
    }
}
