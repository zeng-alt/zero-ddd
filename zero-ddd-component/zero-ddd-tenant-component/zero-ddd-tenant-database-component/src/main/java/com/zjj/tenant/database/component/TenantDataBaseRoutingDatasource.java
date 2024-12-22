package com.zjj.tenant.database.component;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.RemovalListener;
import com.zaxxer.hikari.HikariDataSource;
import com.zjj.exchange.tenant.client.RemoteTenantClient;
import com.zjj.exchange.tenant.domain.Tenant;
import com.zjj.tenant.management.component.config.MultiTenancyProperties;
import com.zjj.tenant.management.component.service.TenantDatabaseService;
import jakarta.annotation.PostConstruct;
import liquibase.integration.spring.SpringLiquibase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月04日 11:42
 */
@Slf4j
@RequiredArgsConstructor
public class TenantDataBaseRoutingDatasource extends AbstractRoutingDataSource implements TenantDatabaseService {

    private LoadingCache<String, DataSource> tenantDataSources;
    private static final String TENANT_POOL_NAME_SUFFIX = "DataSource";
    private final DataSourceProperties dataSourceProperties;
    private final RemoteTenantClient remoteTenantClient;
    private final CurrentTenantIdentifierResolver<String> currentTenantIdentifierResolver;
    private final MultiTenancyProperties multiTenancyProperties;

    @PostConstruct
    private void createCache() {
        tenantDataSources = Caffeine.newBuilder()
                .maximumSize(multiTenancyProperties.getDataSourceCache().getMaximumSize())
                .expireAfterAccess(multiTenancyProperties.getDataSourceCache().getExpireAfterAccess(), TimeUnit.MINUTES)
                .removalListener((RemovalListener<String, DataSource>) (tenantId, dataSource, removalCause) -> {
                    if (dataSource instanceof HikariDataSource hikariDataSource) {
                        hikariDataSource.close(); // tear down properly
                        log.info("Closed datasource: {}", hikariDataSource.getPoolName());
                    }
                })
                .build(tenantId -> remoteTenantClient.findById(tenantId).map(this::createAndConfigureDataSource).getOrElse((DataSource) null));
    }

    public TenantDataBaseRoutingDatasource(
            DataSource dataSource,
            DataSourceProperties dataSourceProperties,
            RemoteTenantClient remoteTenantClient,
            MultiTenancyProperties multiTenancyProperties,
            CurrentTenantIdentifierResolver<String> currentTenantIdentifierResolver
    ) {
        this.setDefaultTargetDataSource(dataSource);
        this.setTargetDataSources(new HashMap<>());

        this.dataSourceProperties = dataSourceProperties;
        this.remoteTenantClient = remoteTenantClient;
        this.multiTenancyProperties = multiTenancyProperties;
        this.currentTenantIdentifierResolver = currentTenantIdentifierResolver;
    }

    public Connection getConnection(String tenantIdentifier) throws SQLException {
        return this.determineTargetDataSource(tenantIdentifier).getConnection();
    }


    protected DataSource determineTargetDataSource(String tenantIdentifier) {
        Assert.notNull(this.tenantDataSources, "DataSource router not initialized");
        DataSource dataSource = this.tenantDataSources.get(tenantIdentifier);
        if (dataSource == null && "master".equals(tenantIdentifier)) {
            dataSource = getResolvedDefaultDataSource();
        }

        if (dataSource == null) {
            throw new IllegalStateException("Cannot determine target DataSource for lookup key [" + tenantIdentifier + "]");
        } else {
            return dataSource;
        }
    }

    private DataSource createAndConfigureDataSource(Tenant tenant) {
//        String decryptedPassword = encryptionService.decrypt(tenant.getPassword(), secret, salt);

        HikariDataSource ds = dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();

        ds.setUsername(tenant.getDb());
        ds.setPassword(tenant.getPassword());
        ds.setJdbcUrl(multiTenancyProperties.getUrlPrefix() + tenant.getDb());

        ds.setPoolName(tenant.getTenantId() + TENANT_POOL_NAME_SUFFIX);

        log.info("Configured datasource: {}", ds.getPoolName());
        return ds;
    }


    @Override
    protected @NonNull Object determineCurrentLookupKey() {
        return currentTenantIdentifierResolver.resolveCurrentTenantIdentifier();
    }

    @Override
    public void addTenantDatabase(String tenant, DataSource dataSource) {
        ConcurrentMap<String, DataSource> map = tenantDataSources.asMap();
        if (map.containsKey(tenant)) {
            DataSource dataSource1 = tenantDataSources.get(tenant);
            if (dataSource1 instanceof HikariDataSource hikariDataSource) {
                hikariDataSource.close();
            }
        }
        tenantDataSources.put(tenant, dataSource);
    }
}
