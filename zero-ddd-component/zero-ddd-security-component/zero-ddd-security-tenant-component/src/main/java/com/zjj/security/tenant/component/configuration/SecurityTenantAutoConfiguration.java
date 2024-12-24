package com.zjj.security.tenant.component.configuration;

import com.zjj.autoconfigure.component.security.SecurityBuilderCustomizer;
import com.zjj.autoconfigure.component.tenant.MultiTenancyProperties;
import com.zjj.autoconfigure.component.tenant.TenantService;
import com.zjj.security.tenant.component.supper.TenantHeaderFilter;
import com.zjj.security.tenant.component.supper.TenantWitchDataSourceFilter;
import org.springframework.beans.factory.ObjectProvider;
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

//    private CacheType cacheType;
//
//    @Bean
//    @ConditionalOnMissingBean(JwtCacheManage.class)
//    public JwtCacheManage tenantJwtCacheManage(RedisStringRepository redisStringRepository, JwtProperties jwtProperties, TenantContextHolder tenantContextHolder, L2CacheManage l2CacheManage) {
//        return switch (cacheType) {
//            case REDIS -> new TenantJwtCacheManage(redisStringRepository, jwtProperties);
//            case L2 -> new TenantJwtL2CacheManage(l2CacheManage);
//        };
//    }

//    @Bean
//    public L2JwtCacheProvider tenantJwtCacheProvider(JwtProperties jwtProperties, TenantService tenantService) {
//        return new L2JwtCacheProvider(jwtProperties, tenantService);
//    }

//    @Bean
//    public TenantHeaderFilter tenantFilter(TenantService tenantService) {
//        return new TenantHeaderFilter(tenantService);
//    }


//    @Bean
//    @ConditionalOnMissingBean
//    public TenantWitchDataSourceFilter tenantWitchDataSourceFilter(DynamicSourceManage dynamicSourceManage, TenantContextHolder tenantContextHolder, JwtProperties jwtProperties) {
//        return new TenantWitchDataSourceFilter(dynamicSourceManage, tenantContextHolder, jwtProperties);
//    }

    @Bean
    public TenantHeaderFilter tenantFilter(ObjectProvider<TenantService> tenantService, MultiTenancyProperties multiTenancyProperties) {
        return new TenantHeaderFilter(tenantService.getIfAvailable(), multiTenancyProperties);
    }

    @Bean
    public TenantWitchDataSourceFilter tenantWitchDataSourceFilter() {
        return new TenantWitchDataSourceFilter();
    }

    @Bean
    @Order(10)
    public SecurityBuilderCustomizer tenantCustomizer(
            TenantHeaderFilter tenantHeaderFilter,
            TenantWitchDataSourceFilter tenantWitchDataSourceFilter
    ) {
        return http -> {
            http.addFilterBefore(tenantHeaderFilter, UsernamePasswordAuthenticationFilter.class);
            http.addFilterAfter(tenantWitchDataSourceFilter, AnonymousAuthenticationFilter.class);
        };
    }
}
