package com.zjj.tenant.management.component.config;

import com.zaxxer.hikari.HikariDataSource;
import com.zjj.exchange.tenant.client.RemoteTenantClient;
import com.zjj.tenant.management.component.spi.DefaultTenantDataSourceProvider;
import com.zjj.tenant.management.component.spi.DefaultTenantSingleDataSourceProvider;
import com.zjj.tenant.management.component.spi.TenantDataSourceProvider;
import com.zjj.tenant.management.component.spi.TenantSingleDataSourceProvider;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ClassUtils;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.ManagedBeanSettings;
import org.hibernate.cfg.MappingSettings;
import org.hibernate.cfg.SchemaToolingSettings;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScanPackages;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.modulith.events.support.CompletionMode;
import org.springframework.orm.hibernate5.SpringBeanContainer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月20日 21:14
 */
@AutoConfiguration
@RequiredArgsConstructor
//@EnableJpaRepositories(
//        basePackages = "com.zjj.example.dao"
//)
@EnableConfigurationProperties({DataSourceProperties.class, JpaProperties.class, HibernateProperties.class})
public class MasterDataSourceConfiguration implements BeanClassLoaderAware {

    private final JpaProperties jpaProperties;
    private final HibernateProperties hibernateProperties;
    private final ConfigurableListableBeanFactory beanFactory;
    private ClassLoader classLoader;

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
            ObjectProvider<HibernatePropertiesCustomizer> hibernatePropertiesCustomizer,
            EntityScanPackages entityScanPackages,
            Environment environment
    ) {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
//        factoryBean.setDataSource(tenantServiceDataSource);
        List<String> packageNames = entityScanPackages.getPackageNames();
        // 判断org.springframework.modulith.events这个包是否存在
        if (classLoader.getResource("org.springframework.modulith.events".replaceAll(".", "/")) != null) {
            packageNames.add("org.springframework.modulith.events.updating");
            CompletionMode property = environment.getProperty(CompletionMode.PROPERTY, CompletionMode.class);
            if (property == CompletionMode.ARCHIVE) {
                packageNames.add("org.springframework.modulith.events.jpa.archiving");
            }
        }
        factoryBean.setPackagesToScan(packageNames.toArray(String[]::new));
        factoryBean.setPersistenceUnitName("masterPersistenceUnit");
        factoryBean.setBeanFactory(beanFactory);

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        factoryBean.setJpaVendorAdapter(vendorAdapter);

        HashMap<String, Object> properties = new HashMap<>(jpaProperties.getProperties());

        properties.put(SchemaToolingSettings.HBM2DDL_AUTO, hibernateProperties.getDdlAuto()); // 确保这里有 update
        properties.put(MappingSettings.PHYSICAL_NAMING_STRATEGY, "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
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

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }


    @Configuration
    @ConditionalOnClass(RemoteTenantClient.class)
    static class TenantDataSourceProviderConfig {
        @Bean
        @ConditionalOnMissingBean(TenantSingleDataSourceProvider.class)
        public TenantSingleDataSourceProvider tenantSingleDataSourceProvider(RemoteTenantClient remoteTenantClient) {
            return new DefaultTenantSingleDataSourceProvider(remoteTenantClient);
        }

        @Bean
        @ConditionalOnMissingBean(TenantDataSourceProvider.class)
        public TenantDataSourceProvider defaultTenantDataSourceProvider(RemoteTenantClient remoteTenantClient) {
            return new DefaultTenantDataSourceProvider(remoteTenantClient);
        }
    }
}
