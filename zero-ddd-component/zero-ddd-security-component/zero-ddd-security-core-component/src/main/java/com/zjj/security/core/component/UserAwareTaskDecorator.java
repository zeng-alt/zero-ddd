package com.zjj.security.core.component;

import com.zjj.autoconfigure.component.security.UserContextHolder;
import com.zjj.autoconfigure.component.tenant.TenantContextHolder;
import org.springframework.core.task.TaskDecorator;
import org.springframework.security.core.Authentication;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月14日 17:37
 */
public class UserAwareTaskDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable runnable) {
//        new DelegatingSecurityContextAsyncTaskExecutor()
        Authentication authentication = UserContextHolder.getAuthentication();
        return () -> {
            try {
                UserContextHolder.setAuthentication(authentication);
                runnable.run();
            } finally {
                TenantContextHolder.clear();
                UserContextHolder.clear();
            }
        };
    }
}
