package com.zjj.tenant.management.component.service;


import com.zjj.autoconfigure.component.tenant.Tenant;
import liquibase.exception.LiquibaseException;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.core.io.ResourceLoader;

import javax.sql.DataSource;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月23日 21:49
 */
public interface TenantDataSourceService {
    void verify(Tenant tenant);

    DataSource createDatasource(Tenant tenant);

    void addTenantDataSource(Tenant tenant, DataSource dataSource);

    void runLiquibase(Tenant tenant, DataSource dataSource, LiquibaseProperties liquibaseProperties, ResourceLoader resourceLoader) throws LiquibaseException;
}
