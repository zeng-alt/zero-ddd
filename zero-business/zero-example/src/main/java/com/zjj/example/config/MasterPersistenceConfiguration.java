//package com.zjj.example.config;
//
//import jakarta.persistence.EntityManagerFactory;
//import lombok.RequiredArgsConstructor;
//import org.hibernate.cfg.ManagedBeanSettings;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
//import org.springframework.boot.autoconfigure.AutoConfiguration;
//import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.annotation.Order;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.hibernate5.SpringBeanContainer;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//
///**
// * @author zengJiaJun
// * @crateTime 2025年03月16日 13:00
// * @version 1.0
// */
//@Configuration
//@EnableConfigurationProperties(JpaProperties.class)
//@EnableJpaRepositories(
//        basePackages = "com.zjj.example.dao"
//)
//public class MasterPersistenceConfiguration {
//
//    @Autowired
//    private ConfigurableListableBeanFactory beanFactory;
//    @Autowired
//    private JpaProperties jpaProperties;
//
//
//    @Bean
//    @Primary
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("masterDataSource") DataSource tenantServiceDataSource) {
//        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
//        factoryBean.setDataSource(tenantServiceDataSource);
//        factoryBean.setPackagesToScan("com.zjj.example.entity", "org.springframework.modulith.events.jpa.updating");
//        factoryBean.setPersistenceUnitName("masterPersistenceUnit");
//        factoryBean.setBeanFactory(beanFactory);
//
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        factoryBean.setJpaVendorAdapter(vendorAdapter);
//
//        HashMap<String, Object> properties = new HashMap<>(jpaProperties.getProperties());
//        properties.put(ManagedBeanSettings.BEAN_CONTAINER, new SpringBeanContainer(this.beanFactory));
//        factoryBean.setJpaPropertyMap(properties);
//        return factoryBean;
//    }
//
//
//    @Bean
//    @Primary
//    public JpaTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory tenantEntityManagerFactory) {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(tenantEntityManagerFactory);
//        return transactionManager;
//    }
//
//
//}
