package com.zjj.security.tenant.component.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月23日 21:19
 */
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class SecurityReactiveTenantAutoConfiguration {
}
