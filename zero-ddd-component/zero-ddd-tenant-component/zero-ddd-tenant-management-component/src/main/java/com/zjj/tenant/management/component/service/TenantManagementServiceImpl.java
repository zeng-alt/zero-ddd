package com.zjj.tenant.management.component.service;

import com.zjj.exchange.tenant.domain.Tenant;
import liquibase.exception.LiquibaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.DataAccessException;

import javax.sql.DataSource;

/**
 * @author zengJiaJun
 * @crateTime 2024年12月22日 16:30
 * @version 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class TenantManagementServiceImpl implements TenantManagementService {


    private final LiquibaseProperties liquibaseProperties;
    private final ResourceLoader resourceLoader;
    private final TenantDataSourceService tenantDataSourceService;
    private final TenantInitDataSourceService tenantInitDataSourceService;

    @Override
    public void createTenant(Tenant tenant) {
        tenantDataSourceService.verify(tenant);

        DataSource dataSource = null;
        try {
            tenantInitDataSourceService.initDataSource(tenant);
            dataSource = tenantDataSourceService.createDatasource(tenant);
        } catch (DataAccessException e) {
            throw new TenantCreationException("Error when creating db: " + tenant.getDb() + " or schema: " + tenant.getSchema(), e);
        }

        if (dataSource != null) {
            try {
                tenantDataSourceService.runLiquibase(tenant, dataSource, liquibaseProperties, resourceLoader);
            } catch (LiquibaseException e) {
                throw new TenantCreationException("Error when populating db: ", e);
            }
        }

        tenantDataSourceService.addTenantDataSource(tenant, dataSource);
    }

}
