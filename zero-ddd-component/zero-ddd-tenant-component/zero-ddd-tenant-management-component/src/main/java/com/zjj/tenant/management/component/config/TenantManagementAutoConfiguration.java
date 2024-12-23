package com.zjj.tenant.management.component.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;

import org.springframework.context.annotation.Import;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月23日 09:05
 */
@AutoConfiguration
@Import({DataSourceConfiguration.class, LiquibaseConfiguration.class})
public class TenantManagementAutoConfiguration {
}
