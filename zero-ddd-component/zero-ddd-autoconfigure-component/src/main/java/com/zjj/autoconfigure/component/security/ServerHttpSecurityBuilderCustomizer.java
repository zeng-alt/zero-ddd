package com.zjj.autoconfigure.component.security;

import org.springframework.security.config.web.server.ServerHttpSecurity;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月26日 21:32
 */
@FunctionalInterface
public interface ServerHttpSecurityBuilderCustomizer {
    void customizer(ServerHttpSecurity httpSecurity);
}
