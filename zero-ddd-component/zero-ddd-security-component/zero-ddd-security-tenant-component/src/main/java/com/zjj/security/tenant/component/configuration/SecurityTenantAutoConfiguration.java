package com.zjj.security.tenant.component.configuration;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import com.zjj.autoconfigure.component.l2cache.L2CacheManage;
import com.zjj.autoconfigure.component.redis.RedisStringRepository;
import com.zjj.autoconfigure.component.security.SecurityBuilderCustomizer;
import com.zjj.autoconfigure.component.security.jwt.JwtCacheManage;
import com.zjj.autoconfigure.component.security.jwt.JwtProperties;
import com.zjj.autoconfigure.component.tenant.TenantProperties;
import com.zjj.cache.component.config.RedisAutoConfiguration;
import com.zjj.l2.cache.component.config.L2CacheConfiguration;
import com.zjj.security.jwt.component.configuration.JwtAutoConfiguration;
import com.zjj.security.tenant.component.enums.CacheType;
import com.zjj.security.tenant.component.supper.*;
import com.zjj.tenant.component.spi.DynamicSourceManage;
import com.zjj.tenant.component.spi.TenantContextHolder;
import com.zjj.tenant.component.supper.DefaultDynamicSourceManage;
import com.zjj.tenant.component.supper.DefaultTenantContextHolder;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import java.util.Map;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月23日 20:39
 */
@AutoConfiguration(after = {L2CacheConfiguration.class, JwtAutoConfiguration.class})
@EnableConfigurationProperties({TenantProperties.class})
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class SecurityTenantAutoConfiguration implements ImportAware {

    private CacheType cacheType;

    @Bean
    @ConditionalOnMissingBean(JwtCacheManage.class)
    public JwtCacheManage tenantJwtCacheManage(RedisStringRepository redisStringRepository, JwtProperties jwtProperties, TenantContextHolder tenantContextHolder, L2CacheManage l2CacheManage) {
        return switch (cacheType) {
            case REDIS -> new TenantJwtCacheManage(redisStringRepository, jwtProperties, tenantContextHolder);
            case L2 -> new TenantJwtL2CacheManage(l2CacheManage, tenantContextHolder);
        };
    }

    @Bean
    public L2JwtCacheProvider tenantJwtCacheProvider(JwtProperties jwtProperties, DynamicSourceManage dynamicSourceManage) {
        return new L2JwtCacheProvider(dynamicSourceManage, jwtProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public TenantFilter tenantFilter(TenantContextHolder tenantManage, DynamicSourceManage dynamicSourceManage) {
        return new TenantFilter(tenantManage, dynamicSourceManage);
    }


    @Bean
    @ConditionalOnMissingBean
    public TenantWitchDataSourceFilter tenantWitchDataSourceFilter(DynamicSourceManage dynamicSourceManage, TenantContextHolder tenantContextHolder, JwtProperties jwtProperties) {
        return new TenantWitchDataSourceFilter(dynamicSourceManage, tenantContextHolder, jwtProperties);
    }

    @Bean
    @Order(10)
    public SecurityBuilderCustomizer tenantCustomizer(
            TenantFilter tenantFilter,
            TenantWitchDataSourceFilter tenantWitchDataSourceFilter
    ) {
        return http -> {
            http.addFilterBefore(tenantFilter, UsernamePasswordAuthenticationFilter.class);
            http.addFilterBefore(tenantWitchDataSourceFilter, UsernamePasswordAuthenticationFilter.class);
        };
    }

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        Map<String, Object> enableTenantAttrMap = importMetadata.getAnnotationAttributes(EnableTenant.class.getName());
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(enableTenantAttrMap);
        Enum<?> cacheType = annotationAttributes.getEnum("cacheType");
        this.cacheType = (CacheType) cacheType;
    }
}
