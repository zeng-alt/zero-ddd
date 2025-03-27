package com.zjj.tenant.datasource.component.configuration;

import com.zjj.domain.component.config.DomainAutoConfiguration;
import com.zjj.tenant.datasource.component.SwitchTenantEventMethodInterceptor;
import com.zjj.tenant.datasource.component.SwitchTenantMethodInterceptor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月24日 20:02
 */
@AutoConfiguration
public class SwitchTenantAutoConfiguration {



    @AutoConfiguration
    public static class SwitchTenantMethodInterceptorRegistrar implements ImportBeanDefinitionRegistrar {

        @Override
        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(SwitchTenantMethodInterceptor.class);
            builder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE); // 关键：避免过早初始化
            registry.registerBeanDefinition("switchTenantMethodInterceptor", builder.getBeanDefinition());
        }
    }

    @AutoConfiguration
    @ConditionalOnClass(DomainAutoConfiguration.class)
    public static class SwitchEventMethodInterceptorRegistrar implements ImportBeanDefinitionRegistrar {

        @Override
        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(SwitchTenantEventMethodInterceptor.class);
            builder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE); // 关键：避免过早初始化
            registry.registerBeanDefinition("switchTenantEventMethodInterceptor", builder.getBeanDefinition());
        }
    }

//    @Lazy
//    @Bean
//    public SwitchTenantMethodInterceptor switchTenantMethodInterceptor() {
//        return new SwitchTenantMethodInterceptor();
//    }

//    @Lazy
//    @Configuration
//    @ConditionalOnClass(DomainAutoConfiguration.class)
//    public static class TenantEventMethodInterceptorRegistrar {
//
//        @Bean
//        public SwitchTenantEventMethodInterceptor switchTenantEventMethodInterceptor() {
//            return new SwitchTenantEventMethodInterceptor();
//        }
//    }
}
