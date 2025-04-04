package com.zjj.tenant.service.component.configuration;

import com.zaxxer.hikari.HikariDataSource;
import com.zjj.tenant.management.component.config.MasterDataSourceConfiguration;
import com.zjj.tenant.management.component.spi.TenantDataSourceProvider;
import com.zjj.tenant.management.component.spi.TenantSingleDataSourceProvider;
import com.zjj.tenant.service.component.exception.CreateTenantDataSourceException;
import com.zjj.tenant.service.component.repository.TenantRepository;
import com.zjj.tenant.service.component.service.SimpleTenantDataSourceService;
import com.zjj.tenant.service.component.service.SimpleTenantSingleDataSourceService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

/**
 * @author zengJiaJun
 * @crateTime 2025年03月16日 12:55
 * @version 1.0
 */
@ConditionalOnBean(name = "masterDataSource")
@AutoConfiguration(after = MasterDataSourceConfiguration.class)
public class TenantServiceAutoConfiguration implements BeanDefinitionRegistryPostProcessor {

    @Bean
    @ConfigurationProperties("multi-tenancy.datasource")
    public DataSourceProperties tenantDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource tenantServiceDataSource(@Qualifier("tenantDataSourceProperties") DataSourceProperties tenantDataSourceProperties) {
        if (!StringUtils.hasText(tenantDataSourceProperties.getUrl())) {
            throw new CreateTenantDataSourceException("multi-tenancy.datasource.url is null");
        }


        HikariDataSource build = tenantDataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();

        build.setPoolName("tenantDataSource");
        return build;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        registry.getBeanDefinition("spring.jpa-org.springframework.boot.autoconfigure.orm.jpa.JpaProperties").setPrimary(true);
        registry.getBeanDefinition("spring.jpa.hibernate-org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties").setPrimary(true);
        if (registry.containsBeanDefinition("tenantDataSource")) {
            BeanDefinition tenantDataSource = registry.getBeanDefinition("tenantDataSource");
            tenantDataSource.setPrimary(true);
        } else {
            BeanDefinition masterDataSource = registry.getBeanDefinition("masterDataSource");
            masterDataSource.setPrimary(true);
        }

    }

    @Bean
    @Primary
    public TenantSingleDataSourceProvider simpleTenantSingleDataSourceProvider(TenantRepository tenantRepository) {
        return new SimpleTenantSingleDataSourceService(tenantRepository);
    }

    @Bean
    @Primary
    public TenantDataSourceProvider simpleTenantDataSourceProvider(TenantRepository tenantRepository) {
        return new SimpleTenantDataSourceService(tenantRepository);
    }


}
