package com.zjj.tenant.management.component.config;

import com.zjj.exchange.tenant.client.RemoteTenantClient;
import com.zjj.exchange.tenant.domain.Tenant;
import com.zjj.tenant.management.component.service.TenantDataSourceService;
import com.zjj.tenant.management.component.service.TenantInitDataSourceService;
import liquibase.exception.LiquibaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.core.io.ResourceLoader;

import javax.sql.DataSource;
import java.util.Collection;

/**
 * Based on MultiTenantSpringLiquibase, this class provides Liquibase support for
 * multi-tenancy based on a dynamic collection of DataSources.
 */
@Slf4j
@RequiredArgsConstructor
public class DynamicDatasourceMultiTenantSpringLiquibase implements InitializingBean {

//    private EncryptionService encryptionService;

    private final RemoteTenantClient remoteTenantClient;
    private final LiquibaseProperties liquibaseProperties;
//    private final MultiTenancyProperties multiTenancyProperties;
    private final ResourceLoader resourceLoader;
    private final TenantInitDataSourceService tenantInitDataSourceService;
    private final TenantDataSourceService tenantDataSourceService;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("DynamicDataSources based multitenancy enabled");
        this.runOnAllTenants(remoteTenantClient.findAll());
    }

    protected void runOnAllTenants(Collection<Tenant> tenants) {
        for(Tenant tenant : tenants) {
            log.info("Initializing Liquibase for tenant " + tenant.getTenantId());
            try {
                tenantDataSourceService.verify(tenant);
                tenantInitDataSourceService.initDataSource(tenant);
                DataSource datasource = tenantDataSourceService.createDatasource(tenant);
                tenantDataSourceService.runLiquibase(tenant, datasource, liquibaseProperties, resourceLoader);
                tenantDataSourceService.addTenantDataSource(tenant, datasource);
            } catch (LiquibaseException e) {
                log.error("Failed to run Liquibase for tenant " + tenant.getTenantId(), e);
            }
            log.info("Liquibase ran for tenant " + tenant.getTenantId());
//            String decryptedPassword = encryptionService.decrypt(tenant.getPassword(), secret, salt);
//            try (Connection connection = DriverManager.getConnection(multiTenancyProperties.getDatabasePattern().getUrlPrefix() + tenant.getDb(), tenant.getDb(), tenant.getPassword())) {
//                DataSource tenantDataSource = new SingleConnectionDataSource(connection, false);
//                SpringLiquibase liquibase = SpringLiquibaseUtils.create(tenantDataSource, liquibaseProperties, resourceLoader);
//                liquibase.afterPropertiesSet();
//            } catch (SQLException | LiquibaseException e) {
//                log.error("Failed to run Liquibase for tenant " + tenant.getTenantId(), e);
//            }
//            log.info("Liquibase ran for tenant " + tenant.getTenantId());
        }
    }

}