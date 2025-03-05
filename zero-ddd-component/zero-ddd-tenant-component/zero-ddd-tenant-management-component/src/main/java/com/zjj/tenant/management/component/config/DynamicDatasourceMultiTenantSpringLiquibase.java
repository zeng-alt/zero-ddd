package com.zjj.tenant.management.component.config;


import com.zjj.autoconfigure.component.tenant.Tenant;
import com.zjj.tenant.management.component.service.TenantDataSourceService;
import com.zjj.tenant.management.component.service.TenantInitDataSourceService;
import com.zjj.tenant.management.component.spi.TenantDataSourceProvider;
import liquibase.exception.LiquibaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Async;

import javax.sql.DataSource;
import java.util.Collection;

/**
 * Based on MultiTenantSpringLiquibase, this class provides Liquibase support for
 * multi-tenancy based on a dynamic collection of DataSources.
 */
@Slf4j
@RequiredArgsConstructor
public class DynamicDatasourceMultiTenantSpringLiquibase implements CommandLineRunner {


    private final LiquibaseProperties liquibaseProperties;
    private final ResourceLoader resourceLoader;
    private final ObjectProvider<TenantDataSourceProvider> tenantDataSourceProviders;
//    private final TenantInitDataSourceService tenantInitDataSourceService;
    private final TenantDataSourceService tenantDataSourceService;


    protected void runOnAllTenants(Collection<Tenant> tenants) {
        for(Tenant tenant : tenants) {
            log.info("Initializing Liquibase for tenant " + tenant.getTenantId());
            try {
                tenantDataSourceService.verify(tenant);
                DataSource datasource = tenantDataSourceService.createDatasource(tenant);
                tenantDataSourceService.runLiquibase(tenant, datasource, liquibaseProperties, resourceLoader);
                tenantDataSourceService.addTenantDataSource(tenant, datasource);
            } catch (LiquibaseException e) {
                log.error("Failed to run Liquibase for tenant " + tenant.getTenantId(), e);
            }
            log.info("Liquibase ran for tenant " + tenant.getTenantId());
        }
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Async
    @Override
    public void run(String... args) throws Exception {
        log.info("DynamicDataSources based multitenancy enabled");
        tenantDataSourceProviders.ifAvailable(t -> this.runOnAllTenants(t.findAll()));
    }
}