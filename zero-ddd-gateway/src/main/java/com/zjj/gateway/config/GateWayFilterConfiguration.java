package com.zjj.gateway.config;

import com.zjj.autoconfigure.component.security.ServerHttpSecurityBuilderCustomizer;
import com.zjj.gateway.filter.CacheRequestFilter;
import com.zjj.gateway.filter.I18nGlobalFilter;
import com.zjj.security.jwt.component.JwtReactiveAuthenticationTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月28日 14:24
 */
@Configuration
public class GateWayFilterConfiguration {

    @Order(9)
    @Bean
    public ServerHttpSecurityBuilderCustomizer gateWayCustomizer(CacheRequestFilter cacheRequestFilter) {
        return http -> http
                .addFilterAt(cacheRequestFilter, SecurityWebFiltersOrder.FIRST);
    }
}
