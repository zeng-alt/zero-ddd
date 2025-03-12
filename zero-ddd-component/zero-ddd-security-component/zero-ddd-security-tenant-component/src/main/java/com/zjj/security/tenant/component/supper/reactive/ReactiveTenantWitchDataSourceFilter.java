package com.zjj.security.tenant.component.supper.reactive;

import com.zjj.autoconfigure.component.tenant.ReactiveTenantContextHolder;
import com.zjj.autoconfigure.component.tenant.TenantContext;
import com.zjj.autoconfigure.component.tenant.TenantContextHolder;
import com.zjj.autoconfigure.component.tenant.TenantDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月26日 10:18
 */
@Slf4j
public class ReactiveTenantWitchDataSourceFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return ReactiveSecurityContextHolder
                .getContext()
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .filter(TenantDetail.class::isInstance)
                .map(o -> (TenantDetail) o)
                .doOnNext(this::setTenantContext)
                .flatMap(c -> chain.filter(exchange).contextWrite(ReactiveTenantContextHolder.withTenant(c.getTenantName(), c.getDatabase(), c.getSchema())))
                .switchIfEmpty(chain.filter(exchange));
    }

    public String setTenantContext(String tenantDetail) {
        return tenantDetail;
    }

    public TenantDetail setTenantContext(TenantDetail tenantDetail) {
        TenantContextHolder.setTenantId(tenantDetail.getTenantName());
        return tenantDetail;
    }
}
