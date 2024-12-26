package com.zjj.security.core.component.spi;

import org.springframework.security.authorization.AuthorizationManager;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月26日 21:58
 */
public interface AuthorizationManagerProvider<T> {

    AuthorizationManager<T> get();
}
