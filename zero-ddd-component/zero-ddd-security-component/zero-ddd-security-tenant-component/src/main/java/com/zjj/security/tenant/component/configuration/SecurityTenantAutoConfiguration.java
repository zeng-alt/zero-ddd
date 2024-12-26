package com.zjj.security.tenant.component.configuration;

import com.zjj.autoconfigure.component.security.SecurityBuilderCustomizer;
import com.zjj.autoconfigure.component.tenant.MultiTenancyProperties;
import com.zjj.autoconfigure.component.tenant.TenantService;
import com.zjj.security.tenant.component.supper.TenantHeaderFilter;
import com.zjj.security.tenant.component.supper.TenantWitchDataSourceFilter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月23日 20:39
 */
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass({ EnableWebSecurity.class })
public class SecurityTenantAutoConfiguration {

    @Value("${multi-tenancy.tenant-token:X-TENANT-ID}")
    private String tenantToken;

    @Bean
    public TenantWitchDataSourceFilter tenantWitchDataSourceFilter() {
        return new TenantWitchDataSourceFilter();
    }

    @Bean
    @Order(10)
    public SecurityBuilderCustomizer tenantCustomizer(ObjectProvider<TenantService> tenantService, ObjectProvider<MultiTenancyProperties> multiTenancyProperties) {
        TenantWitchDataSourceFilter tenantWitchDataSourceFilter = new TenantWitchDataSourceFilter();
        MultiTenancyProperties properties = multiTenancyProperties.getIfAvailable();
        TenantHeaderFilter tenantHeaderFilter = new TenantHeaderFilter(tenantService.getIfAvailable(), properties == null ? tenantToken : properties.getTenantToken());
        return http -> {
            http.addFilterBefore(tenantHeaderFilter, UsernamePasswordAuthenticationFilter.class);
            http.addFilterAfter(tenantWitchDataSourceFilter, AnonymousAuthenticationFilter.class);
        };
    }
}
