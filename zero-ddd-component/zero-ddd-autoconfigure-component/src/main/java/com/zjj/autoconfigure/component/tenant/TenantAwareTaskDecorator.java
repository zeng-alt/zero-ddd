package com.zjj.autoconfigure.component.tenant;

import org.springframework.core.task.TaskDecorator;
import org.springframework.lang.NonNull;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月03日 21:19
 */
public class TenantAwareTaskDecorator implements TaskDecorator {

    @Override
    @NonNull
    public Runnable decorate(@NonNull Runnable runnable) {
        String tenantId = TenantContextHolder.getTenantId();
        return () -> {
            try {
                TenantContextHolder.setTenantId(tenantId);
                runnable.run();
            } finally {
                TenantContextHolder.setTenantId(null);
            }
        };
    }
}
