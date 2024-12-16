package com.zjj.security.abac.component.spi;

import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月13日 21:14
 */
public interface EnvironmentAttribute {
    Map<String, Object> getEnvironment(Authentication authentication);
}
