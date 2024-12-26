package com.zjj.security.core.component.spi;

import org.springframework.security.authorization.ReactiveAuthorizationManager;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月26日 21:53
 */
public interface ReactiveAuthorizationManagerProvider<T> {

    ReactiveAuthorizationManager<T> get();
}
