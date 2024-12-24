package com.zjj.tenant.management.component.config;

import com.zjj.exchange.tenant.client.RemoteTenantClient;
import com.zjj.tenant.management.component.utils.SpringLiquibaseUtils;
import liquibase.integration.spring.SpringLiquibase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;

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
    private final ResourceLoader resourceLoader;
    private final RemoteTenantClient remoteTenantClient;

//    @Bean
//    public SpringLiquibase masterLiquibase(ObjectProvider<DataSource> liquibaseDataSource) {
////        SpringLiquibase liquibase = new SpringLiquibase();
////        liquibase.setDataSource(liquibaseDataSource.getIfAvailable());
////        liquibase.setChangeLog(liquibaseProperties.getChangeLog());
////        liquibase.setContexts(CollectionUtils.isEmpty(liquibaseProperties.getContexts()) ? null : liquibaseProperties.getContexts().get(0));
////        liquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema());
////        liquibase.setLiquibaseSchema(liquibaseProperties.getLiquibaseSchema());
////        liquibase.setLiquibaseTablespace(liquibaseProperties.getLiquibaseTablespace());
////        liquibase.setDatabaseChangeLogTable(liquibaseProperties.getDatabaseChangeLogTable());
////        liquibase.setDatabaseChangeLogLockTable(liquibaseProperties.getDatabaseChangeLogLockTable());
////        liquibase.setDropFirst(liquibaseProperties.isDropFirst());
////        liquibase.setShouldRun(liquibaseProperties.isEnabled());
////        liquibase.setLabelFilter(CollectionUtils.isEmpty(liquibaseProperties.getLabelFilter()) ? null : liquibaseProperties.getLabelFilter().get(0));
////        liquibase.setChangeLogParameters(liquibaseProperties.getParameters());
////        liquibase.setRollbackFile(liquibaseProperties.getRollbackFile());
////        liquibase.setTestRollbackOnUpdate(liquibaseProperties.isTestRollbackOnUpdate());
////        return liquibase;
//
//        return SpringLiquibaseUtils.create(liquibaseDataSource.getIfAvailable(), liquibaseProperties, resourceLoader);
//    }
}
