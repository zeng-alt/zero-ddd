package com.zjj.tenant.domain.tenant;

import io.vavr.control.Option;
import org.springframework.lang.NonNull;

import java.util.function.Supplier;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月30日 21:24
 */
public interface TenantRepository {

    Option<ITenant> findByTenantKey(String tenantKey);

    default Option<ITenant> findByTenantKey(@NonNull Supplier<String> tenantKey) {
        return findByTenantKey(tenantKey.get());
    }

    Option<ITenant> findById(Long id);

    ITenant save(ITenant tenant);
}
