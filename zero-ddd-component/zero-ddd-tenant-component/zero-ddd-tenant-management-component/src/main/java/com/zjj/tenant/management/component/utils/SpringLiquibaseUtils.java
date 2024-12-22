package com.zjj.tenant.management.component.utils;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;

/**
 * @author zengJiaJun
 * @crateTime 2024年12月22日 21:54
 * @version 1.0
 */
public class SpringLiquibaseUtils {

    public static SpringLiquibase create(DataSource dataSource, LiquibaseProperties liquibaseProperties, ResourceLoader resourceLoader) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setResourceLoader(resourceLoader);
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(liquibaseProperties.getChangeLog());
        liquibase.setContexts(CollectionUtils.isEmpty(liquibaseProperties.getContexts()) ? null : liquibaseProperties.getContexts().get(0));
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
        return liquibase;
    }
}
