package com.zjj.security.tenant.component.configuration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.web.server.WebFilterChainProxy;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月23日 21:19
 */
@AutoConfiguration
@ConditionalOnClass({ EnableWebFluxSecurity.class, WebFilterChainProxy.class, })
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class SecurityReactiveTenantAutoConfiguration {
}
