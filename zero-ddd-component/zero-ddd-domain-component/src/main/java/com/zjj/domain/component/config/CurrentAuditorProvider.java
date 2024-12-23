package com.zjj.domain.component.config;

import com.zjj.autoconfigure.component.security.UserContextHolder;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月13日 21:37
 */
public class CurrentAuditorProvider implements AuditorAware<String> {


    /**
     * Returns the current auditor of the application.
     *
     * @return the current auditor.
     */
    @Override
    public @NonNull Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(UserContextHolder.getUsername());
    }
}
