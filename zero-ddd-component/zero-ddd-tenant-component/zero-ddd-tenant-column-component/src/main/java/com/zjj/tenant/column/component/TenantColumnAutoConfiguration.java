package com.zjj.tenant.column.component;

import com.zjj.autoconfigure.component.tenant.MultiTenancyProperties;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月23日 09:14
 */
@AutoConfiguration
public class TenantColumnAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public CurrentTenantIdentifierResolver<String> tenantIdentifierResolver() {
        return new TenantIdentifierResolver();
    }

    @Bean
    public HibernatePropertiesCustomizer tenantColumnHibernatePropertiesCustomizer(CurrentTenantIdentifierResolver<String> currentTenantIdentifierResolver) {
        return hibernateProperties -> {
            hibernateProperties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolver);
        };
    }
}
