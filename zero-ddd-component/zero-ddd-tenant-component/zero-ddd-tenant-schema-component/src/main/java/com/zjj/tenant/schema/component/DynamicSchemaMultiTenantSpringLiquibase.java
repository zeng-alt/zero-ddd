//package com.zjj.tenant.schema.component;
//
//import com.zjj.autoconfigure.component.tenant.MultiTenancyProperties;
//import com.zjj.exchange.tenant.client.RemoteTenantClient;
//import com.zjj.exchange.tenant.domain.Tenant;
//import com.zjj.tenant.management.component.service.TenantDataSourceService;
//import com.zjj.tenant.management.component.service.TenantInitDataSourceService;
//import liquibase.exception.LiquibaseException;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
//import org.springframework.core.io.ResourceLoader;
//
//import javax.sql.DataSource;
//import java.util.Collection;
//
///**
// * @author zengJiaJun
// * @version 1.0
// * @crateTime 2024年12月26日 21:52
// */
//@Slf4j
//@RequiredArgsConstructor
//public class DynamicSchemaMultiTenantSpringLiquibase implements InitializingBean {
//
//    private final RemoteTenantClient remoteTenantClient;
//    private final LiquibaseProperties liquibaseProperties;
//    private final ResourceLoader resourceLoader;
//    private final TenantInitDataSourceService tenantInitDataSourceService;
//    private final TenantDataSourceService tenantDataSourceService;
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        log.info("DynamicDataSources based multitenancy enabled");
//        this.runOnAllTenants(remoteTenantClient.findAll());
//    }
//
//    protected void runOnAllTenants(Collection<Tenant> tenants) {
//        for(Tenant tenant : tenants) {
//            log.info("Initializing Liquibase for tenant " + tenant.getTenantId());
//            try {
//                tenantDataSourceService.verify(tenant);
//                DataSource datasource = tenantDataSourceService.createDatasource(tenant);
//                tenantInitDataSourceService.initDataSource(tenant);
//                tenantDataSourceService.runLiquibase(tenant, datasource, liquibaseProperties, resourceLoader);
//                tenantDataSourceService.addTenantDataSource(tenant, datasource);
//            } catch (LiquibaseException e) {
//                log.error("Failed to run Liquibase for tenant " + tenant.getTenantId(), e);
//            }
//
//            log.info("Liquibase ran for tenant " + tenant.getTenantId());
//        }
//    }
//}
