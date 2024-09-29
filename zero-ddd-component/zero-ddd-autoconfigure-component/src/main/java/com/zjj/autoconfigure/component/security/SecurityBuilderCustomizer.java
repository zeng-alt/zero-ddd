package com.zjj.autoconfigure.component.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月29日 20:18
 */
@FunctionalInterface
public interface SecurityBuilderCustomizer {

    void customize(HttpSecurity http) throws Exception;
}
