package com.zjj.tenant.datasource.component.configuration;

import com.zjj.domain.component.config.DomainAutoConfiguration;
import com.zjj.tenant.datasource.component.SwitchTenantEventMethodInterceptor;
import com.zjj.tenant.datasource.component.SwitchTenantMethodInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月24日 20:02
 */
@AutoConfiguration
public class SwitchTenantAutoConfiguration {


    @Lazy
    @Bean
    public SwitchTenantMethodInterceptor switchTenantMethodInterceptor() {
        return new SwitchTenantMethodInterceptor();
    }

    @Lazy
    @Configuration
    @ConditionalOnClass(DomainAutoConfiguration.class)
    public static class TenantEventMethodInterceptorRegistrar {

        @Bean
        public SwitchTenantEventMethodInterceptor switchTenantEventMethodInterceptor() {
            return new SwitchTenantEventMethodInterceptor();
        }
    }
}
