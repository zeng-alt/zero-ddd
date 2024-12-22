package com.zjj.tenant.database.component.config.tenant.liquibase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import javax.sql.DataSource;

import com.zjj.exchange.tenant.client.RemoteTenantClient;
import com.zjj.exchange.tenant.domain.Tenant;
import com.zjj.tenant.management.component.config.MultiTenancyProperties;
import com.zjj.tenant.management.component.service.EncryptionService;
import com.zjj.tenant.management.component.utils.SpringLiquibaseUtils;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * Based on MultiTenantSpringLiquibase, this class provides Liquibase support for
 * multi-tenancy based on a dynamic collection of DataSources.
 */
@Slf4j
@RequiredArgsConstructor
public class DynamicDataSourceBasedMultiTenantSpringLiquibase implements InitializingBean {

//    private EncryptionService encryptionService;

    private final RemoteTenantClient remoteTenantClient;
    private final LiquibaseProperties liquibaseProperties;
    private final MultiTenancyProperties multiTenancyProperties;
    private final ResourceLoader resourceLoader;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("DynamicDataSources based multitenancy enabled");
        this.runOnAllTenants(remoteTenantClient.findAll());
    }

    protected void runOnAllTenants(Collection<Tenant> tenants) {
        for(Tenant tenant : tenants) {
            log.info("Initializing Liquibase for tenant " + tenant.getTenantId());
//            String decryptedPassword = encryptionService.decrypt(tenant.getPassword(), secret, salt);
            try (Connection connection = DriverManager.getConnection(multiTenancyProperties.getUrlPrefix() + tenant.getDb(), tenant.getDb(), tenant.getPassword())) {
                DataSource tenantDataSource = new SingleConnectionDataSource(connection, false);
                SpringLiquibase liquibase = SpringLiquibaseUtils.create(tenantDataSource, liquibaseProperties, resourceLoader);
                liquibase.afterPropertiesSet();
            } catch (SQLException | LiquibaseException e) {
                log.error("Failed to run Liquibase for tenant " + tenant.getTenantId(), e);
            }
            log.info("Liquibase ran for tenant " + tenant.getTenantId());
        }
    }

}