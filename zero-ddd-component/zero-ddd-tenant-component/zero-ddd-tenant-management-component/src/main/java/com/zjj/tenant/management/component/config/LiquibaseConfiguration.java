package com.zjj.tenant.management.component.config;

import com.zjj.autoconfigure.component.tenant.MultiTenancyProperties;
import com.zjj.exchange.tenant.client.RemoteTenantClient;
import liquibase.integration.spring.SpringLiquibase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月20日 21:14
 */
@AutoConfiguration
@RequiredArgsConstructor
@EnableConfigurationProperties(LiquibaseProperties.class)
public class LiquibaseConfiguration {

    private final LiquibaseProperties liquibaseProperties;
    private final DataSourceProperties dataSourceProperties;
    private final MultiTenancyProperties multiTenancyProperties;

    @Bean
    @ConditionalOnProperty(name = "spring.liquibase.enabled", havingValue = "true")
    public SpringLiquibase masterLiquibase(@LiquibaseDataSource ObjectProvider<DataSource> liquibaseDataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(liquibaseDataSource.getIfAvailable());
        liquibase.setChangeLog(liquibaseProperties.getChangeLog());
        liquibase.setContexts(CollectionUtils.isEmpty(liquibaseProperties.getContexts()) ? null : liquibaseProperties.getContexts().get(0));
        liquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema());
        liquibase.setLiquibaseSchema(liquibaseProperties.getLiquibaseSchema());
        liquibase.setLiquibaseTablespace(liquibaseProperties.getLiquibaseTablespace());
        liquibase.setDatabaseChangeLogTable(liquibaseProperties.getDatabaseChangeLogTable());
        liquibase.setDatabaseChangeLogLockTable(liquibaseProperties.getDatabaseChangeLogLockTable());
        liquibase.setDropFirst(liquibaseProperties.isDropFirst());
        liquibase.setShouldRun(liquibaseProperties.isEnabled());
        liquibase.setLabelFilter(CollectionUtils.isEmpty(liquibaseProperties.getLabelFilter()) ? null : liquibaseProperties.getLabelFilter().get(0));
        liquibase.setChangeLogParameters(liquibaseProperties.getParameters());
        liquibase.setRollbackFile(liquibaseProperties.getRollbackFile());
        liquibase.setTestRollbackOnUpdate(liquibaseProperties.isTestRollbackOnUpdate());
        Map<String, String> parameters = new HashMap<>();
        if (liquibaseProperties.getParameters() != null) {
            parameters.putAll(liquibaseProperties.getParameters());
        }
        parameters.put("tenantName", multiTenancyProperties.getMaster());
        parameters.put("username", dataSourceProperties.getUsername());
        parameters.put("schema", multiTenancyProperties.getSchemaPattern().getSchema());
        liquibase.setChangeLogParameters(parameters);
        return liquibase;

    }
}
