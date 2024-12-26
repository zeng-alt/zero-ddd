package com.zjj.autoconfigure.component.tenant;

import com.zjj.autoconfigure.component.security.UserContextHolder;
import org.springframework.core.task.TaskDecorator;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;

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
        Authentication authentication = UserContextHolder.getAuthentication();
        return () -> {
            try {
                TenantContextHolder.setTenantId(tenantId);
                UserContextHolder.setAuthentication(authentication);
                runnable.run();
            } finally {
                TenantContextHolder.clear();
                UserContextHolder.clear();
            }
        };
    }
}
