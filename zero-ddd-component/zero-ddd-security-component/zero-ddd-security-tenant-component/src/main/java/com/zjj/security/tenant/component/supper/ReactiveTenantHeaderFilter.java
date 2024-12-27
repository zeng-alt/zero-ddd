package com.zjj.security.tenant.component.supper;

import com.zjj.autoconfigure.component.tenant.ReactiveTenantContextHolder;
import com.zjj.autoconfigure.component.tenant.TenantContext;
import com.zjj.autoconfigure.component.tenant.TenantContextHolder;
import com.zjj.autoconfigure.component.tenant.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月26日 21:18
 */
@Slf4j
@RequiredArgsConstructor
public class ReactiveTenantHeaderFilter implements WebFilter {

    private final TenantService tenantService;
    private final String tenantToken;
    @Setter
    private String master;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String tenant = resolveTenant(exchange.getRequest());

        if (!StringUtils.hasText(tenant)) {
            tenant = master;
        }

        boolean hasAccess = isUserAllowed(tenant);
        if (!hasAccess) {
            throw new AccessDeniedException("tenant Access denied");
        }

        log.debug("切换数据源为：{}", tenant);

        String finalTenant = tenant;
        return chain.filter(exchange)
                .contextWrite((context) -> context.hasKey(TenantContext.class) ? context
                        : ReactiveTenantContextHolder.withTenant(finalTenant));

    }

    private boolean isUserAllowed(String tenant) {
        if (tenantService == null) {
            return true;
        }
        return tenantService.getTenants().contains(tenant);
    }

    private @Nullable String resolveTenant(ServerHttpRequest request) {
        return request.getHeaders().getFirst(tenantToken);
    }
}
