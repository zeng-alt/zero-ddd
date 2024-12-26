package com.zjj.autoconfigure.component.tenant;

import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.function.Function;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月26日 21:25
 */
public final class ReactiveTenantContextHolder {

    private static final Class<?> TENANT_CONTEXT_KEY = TenantContext.class;

    private ReactiveTenantContextHolder() {}

    public static Mono<TenantContext> getContext() {
        // @formatter:off
        return Mono.deferContextual(Mono::just)
                .cast(Context.class)
                .filter(ReactiveTenantContextHolder::hasTenantContext)
                .flatMap(ReactiveTenantContextHolder::getTenantContext);
        // @formatter:on
    }


    public static Context withTenantContext(Mono<? extends TenantContext> tenantContext) {
        return Context.of(TENANT_CONTEXT_KEY, tenantContext);
    }

    public static Function<Context, Context> clearContext() {
        return (context) -> context.delete(TENANT_CONTEXT_KEY);
    }

    public static Context withTenant(String tenant) {
        return withTenantContext(Mono.just(new TenantContextImpl(tenant)));
    }

    private static Mono<TenantContext> getTenantContext(Context context) {
        return context.<Mono<TenantContext>>get(TENANT_CONTEXT_KEY);
    }

    private static boolean hasTenantContext(Context context) {
        return context.hasKey(TENANT_CONTEXT_KEY);
    }
}
