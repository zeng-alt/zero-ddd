package com.zjj.tenant.service.component.configuration;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.ManagedBeanSettings;
import org.hibernate.cfg.SchemaToolingSettings;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.SpringBeanContainer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * @author zengJiaJun
 * @crateTime 2025年03月16日 13:00
 * @version 1.0
 */
@AutoConfiguration
@EnableConfigurationProperties({JpaProperties.class, HibernateProperties.class})
@EnableJpaRepositories(
        basePackages = "com.zjj.tenant.service.component.repository",
        entityManagerFactoryRef = "tenantEntityManagerFactory",
        transactionManagerRef = "tenantTransactionManager"
)
@RequiredArgsConstructor

public class TenantPersistenceConfiguration {

    private final ConfigurableListableBeanFactory beanFactory;

    @Value("${multi-tenancy.entity.packages:com.zjj.tenant.service.component.entity}")
    private String entityPackages;

    @Bean
    @ConfigurationProperties("multi-tenancy.jpa.hibernate")
    public HibernateProperties tenantHibernateProperties() {
        return new HibernateProperties();
    }

    @Bean
    @ConfigurationProperties("multi-tenancy.jpa")
    public JpaProperties tenantJpaProperties() {
        return new JpaProperties();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean tenantEntityManagerFactory(
            @Qualifier("tenantServiceDataSource") DataSource tenantServiceDataSource,
            @Qualifier("tenantJpaProperties") JpaProperties jpaProperties,
            @Qualifier("tenantHibernateProperties") HibernateProperties hibernateProperties
    ) {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(tenantServiceDataSource);
        factoryBean.setPackagesToScan(entityPackages);
        factoryBean.setPersistenceUnitName("tenantPersistenceUnit");
        factoryBean.setBeanFactory(beanFactory);

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        factoryBean.setJpaVendorAdapter(vendorAdapter);

        HashMap<String, Object> properties = new HashMap<>(jpaProperties.getProperties());
        properties.put(SchemaToolingSettings.HBM2DDL_AUTO, hibernateProperties.getDdlAuto());
        properties.put(AvailableSettings.PHYSICAL_NAMING_STRATEGY, "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
        properties.put(AvailableSettings.IMPLICIT_NAMING_STRATEGY, "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
        properties.put(ManagedBeanSettings.BEAN_CONTAINER, new SpringBeanContainer(this.beanFactory));
        factoryBean.setJpaPropertyMap(properties);
        return factoryBean;
    }


    @Bean
    public JpaTransactionManager tenantTransactionManager(@Qualifier("tenantEntityManagerFactory") EntityManagerFactory tenantEntityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(tenantEntityManagerFactory);
        return transactionManager;
    }


}
