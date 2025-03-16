package com.zjj.tenant.management.component.config;

import com.zaxxer.hikari.HikariDataSource;
import com.zjj.exchange.tenant.client.RemoteTenantClient;
import com.zjj.tenant.management.component.spi.DefaultTenantDataSourceProvider;
import com.zjj.tenant.management.component.spi.DefaultTenantSingleDataSourceProvider;
import com.zjj.tenant.management.component.spi.TenantDataSourceProvider;
import com.zjj.tenant.management.component.spi.TenantSingleDataSourceProvider;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.ManagedBeanSettings;
import org.hibernate.cfg.SchemaToolingSettings;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.SpringBeanContainer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月20日 21:14
 */
@RequiredArgsConstructor
@EnableJpaRepositories(
        basePackages = "com.zjj.example.dao"
)
@EnableConfigurationProperties({DataSourceProperties.class, JpaProperties.class, HibernateProperties.class})
public class DataSourceConfiguration {

    private final JpaProperties jpaProperties;
    private final HibernateProperties hibernateProperties;
    private final ConfigurableListableBeanFactory beanFactory;

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties masterDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @LiquibaseDataSource
    public DataSource masterDataSource(@Qualifier("masterDataSourceProperties") DataSourceProperties dataSourceProperties) {
        HikariDataSource dataSource = dataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();

        dataSource.setPoolName("masterDataSource");
        return dataSource;
    }


    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
//            @Qualifier("masterDataSource") DataSource tenantServiceDataSource
            ObjectProvider<HibernatePropertiesCustomizer> hibernatePropertiesCustomizer
    ) {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
//        factoryBean.setDataSource(tenantServiceDataSource);
        factoryBean.setPackagesToScan("com.zjj.example.entity", "org.springframework.modulith.events.jpa.updating");
        factoryBean.setPersistenceUnitName("masterPersistenceUnit");
        factoryBean.setBeanFactory(beanFactory);

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        factoryBean.setJpaVendorAdapter(vendorAdapter);

        HashMap<String, Object> properties = new HashMap<>(jpaProperties.getProperties());

        properties.put(SchemaToolingSettings.HBM2DDL_AUTO, hibernateProperties.getDdlAuto()); // 确保这里有 update
        properties.put(AvailableSettings.PHYSICAL_NAMING_STRATEGY, "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
        properties.put(AvailableSettings.IMPLICIT_NAMING_STRATEGY, "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
        properties.put(ManagedBeanSettings.BEAN_CONTAINER, new SpringBeanContainer(this.beanFactory));
        hibernatePropertiesCustomizer.orderedStream().forEach(customizer -> customizer.customize(properties));
        factoryBean.setJpaPropertyMap(properties);
        return factoryBean;
    }


    @Bean
    @Primary
    public JpaTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory tenantEntityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(tenantEntityManagerFactory);
        return transactionManager;
    }


    @AutoConfiguration
    @ConditionalOnClass(RemoteTenantClient.class)
    static class TenantDataSourceProviderConfig {
        @Bean
        @Lazy
        @ConditionalOnMissingBean(TenantSingleDataSourceProvider.class)
        public TenantSingleDataSourceProvider tenantSingleDataSourceProvider(RemoteTenantClient remoteTenantClient) {
            return new DefaultTenantSingleDataSourceProvider(remoteTenantClient);
        }

        @Bean
        @Lazy
        @ConditionalOnMissingBean(TenantDataSourceProvider.class)
        public TenantDataSourceProvider defaultTenantDataSourceProvider(RemoteTenantClient remoteTenantClient) {
            return new DefaultTenantDataSourceProvider(remoteTenantClient);
        }
    }
}
