package com.zjj.tenant.management.component.service;


import com.zjj.autoconfigure.component.tenant.Tenant;
import liquibase.exception.LiquibaseException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.core.io.ResourceLoader;

import javax.sql.DataSource;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月23日 21:49
 */
public interface TenantDataSourceService extends InitializingBean {

    void verify(Tenant tenant);

    /**
     * 如果是database 模式，则创建database
     * 如果是schema模式，则创建schema
     * 最后要返回数据源
     * @return 数据源
     */
    DataSource createDatasource(Tenant tenant);

    void addTenantDataSource(Tenant tenant, DataSource dataSource);

    void runLiquibase(Tenant tenant, DataSource dataSource, LiquibaseProperties liquibaseProperties, ResourceLoader resourceLoader) throws LiquibaseException;
}
