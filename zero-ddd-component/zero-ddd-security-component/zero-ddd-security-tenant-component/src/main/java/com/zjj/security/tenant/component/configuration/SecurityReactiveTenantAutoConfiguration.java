package com.zjj.security.tenant.component.configuration;

import com.zjj.autoconfigure.component.security.ServerHttpSecurityBuilderCustomizer;
import com.zjj.autoconfigure.component.tenant.MultiTenancyProperties;
import com.zjj.autoconfigure.component.tenant.TenantService;
import com.zjj.security.tenant.component.supper.ReactiveTenantHeaderFilter;
import com.zjj.security.tenant.component.supper.ReactiveTenantWitchDataSourceFilter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.web.server.WebFilterChainProxy;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月23日 21:19
 */
@AutoConfiguration
@ConditionalOnClass({ EnableWebFluxSecurity.class, WebFilterChainProxy.class, })
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class SecurityReactiveTenantAutoConfiguration {

    @Value("${multi-tenancy.tenant-token:X-TENANT-ID}")
    private String tenantToken;

    @Bean
    public ServerHttpSecurityBuilderCustomizer tenantCustomizer(ObjectProvider<TenantService> tenantService, ObjectProvider<MultiTenancyProperties> multiTenancyProperties) {
        MultiTenancyProperties properties = multiTenancyProperties.getIfAvailable();
        ReactiveTenantHeaderFilter reactiveTenantHeaderFilter = new ReactiveTenantHeaderFilter(tenantService.getIfAvailable(), properties == null ? tenantToken : properties.getTenantToken());
        ReactiveTenantWitchDataSourceFilter reactiveTenantWitchDataSourceFilter = new ReactiveTenantWitchDataSourceFilter();
        return (http) -> {
            http.addFilterAfter(reactiveTenantHeaderFilter, SecurityWebFiltersOrder.REACTOR_CONTEXT);
            http.addFilterBefore(reactiveTenantWitchDataSourceFilter, SecurityWebFiltersOrder.FORM_LOGIN);
        };
    }
}
