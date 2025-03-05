package com.zjj.tenant.database.component;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.RemovalListener;
import com.zaxxer.hikari.HikariDataSource;
import com.zjj.autoconfigure.component.tenant.MultiTenancyProperties;
import com.zjj.autoconfigure.component.tenant.Tenant;
import com.zjj.tenant.management.component.SpringLiquibase;
import com.zjj.tenant.management.component.service.TenantCreationException;
import com.zjj.tenant.management.component.service.TenantDataSourceService;
import com.zjj.tenant.management.component.service.TenantInitDataSourceService;
import com.zjj.tenant.management.component.spi.TenantSingleDataSourceProvider;
import com.zjj.tenant.management.component.utils.SpringLiquibaseUtils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import liquibase.exception.LiquibaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

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
public class TenantDataBaseRoutingDatasource extends AbstractRoutingDataSource implements TenantDataSourceService, TenantConnectionService {
    private static final String TENANT_POOL_NAME_SUFFIX = "DataSource";
    private static final String VALID_DATABASE_NAME_REGEXP = "\\w*";
    private LoadingCache<String, DataSource> tenantDataSources;

    private final DataSourceProperties dataSourceProperties;
    private final TenantSingleDataSourceProvider tenantSingleDataSourceProvider;
    private final CurrentTenantIdentifierResolver<String> currentTenantIdentifierResolver;
    private final MultiTenancyProperties multiTenancyProperties;
    private final TenantInitDataSourceService tenantDatabaseInitService;

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
                .build(tenantId -> tenantSingleDataSourceProvider.findById(tenantId).map(this::createAndConfigureDataSource).getOrElse((DataSource) null));
    }

    public TenantDataBaseRoutingDatasource(
            DataSource dataSource,
            DataSourceProperties dataSourceProperties,
            TenantSingleDataSourceProvider tenantSingleDataSourceProvider,
            MultiTenancyProperties multiTenancyProperties,
            CurrentTenantIdentifierResolver<String> currentTenantIdentifierResolver,
            TenantInitDataSourceService tenantDatabaseInitService
    ) {
        this.setDefaultTargetDataSource(dataSource);
        this.setTargetDataSources(new HashMap<>());

        this.dataSourceProperties = dataSourceProperties;
        this.tenantSingleDataSourceProvider = tenantSingleDataSourceProvider;
        this.multiTenancyProperties = multiTenancyProperties;
        this.currentTenantIdentifierResolver = currentTenantIdentifierResolver;
        this.tenantDatabaseInitService = tenantDatabaseInitService;
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        return this.determineTargetDataSource(tenantIdentifier).getConnection();
    }


    protected DataSource determineTargetDataSource(String tenantIdentifier) {
        Assert.notNull(this.tenantDataSources, "DataSource router not initialized");
        DataSource dataSource = null;
        if (multiTenancyProperties.getMaster().equals(tenantIdentifier)) {
            dataSource = getResolvedDefaultDataSource();
        }

        if (dataSource == null) {
            dataSource = this.tenantDataSources.get(tenantIdentifier);
        }

        if (dataSource == null) {
            throw new TenantDataSourceException("tenant.datasource.not.exist", (Object) tenantIdentifier);
        } else {
            return dataSource;
        }
    }

    private DataSource createAndConfigureDataSource(Tenant tenant) {

        verify(tenant);
//        tenantDatabaseInitService.initDataSource(tenant, getResolvedDefaultDataSource());
        DataSource datasource = createDatasource(tenant);

////        String decryptedPassword = encryptionService.decrypt(tenant.getPassword(), secret, salt);
//
//        HikariDataSource ds = dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
//
//        ds.setUsername(tenant.getDb());
//        ds.setPassword(tenant.getPassword());
//        ds.setJdbcUrl(multiTenancyProperties.getDatabasePattern().getUrlPrefix() + tenant.getDb());
//
//        ds.setPoolName(tenant.getTenantId() + TENANT_POOL_NAME_SUFFIX);
//
//        log.info("Configured datasource: {}", ds.getPoolName());
        return datasource;
    }


    @Override
    protected @NonNull Object determineCurrentLookupKey() {
        return currentTenantIdentifierResolver.resolveCurrentTenantIdentifier();
    }

    @Override
    public void verify(Tenant tenant) {
        if (tenant != null || !tenant.getDb().matches(VALID_DATABASE_NAME_REGEXP)) {
            throw new TenantCreationException("Invalid db name: " + tenant.getDb());
        }
    }

    @Override
    public DataSource createDatasource(Tenant tenant) {

        this.tenantDatabaseInitService.initDataSource(tenant, getResolvedDefaultDataSource());

        HikariDataSource ds = dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();


        if (StringUtils.hasText(tenant.getDb())) {
            ds.setUsername(tenant.getDb());
        }
        ds.setPassword(tenant.getPassword());
        ds.setJdbcUrl(multiTenancyProperties.getDatabasePattern().getUrlPrefix() + tenant.getDb());

        ds.setPoolName(tenant.getTenantId());

        log.info("Configured datasource: {}", ds.getPoolName());
        return ds;
    }

    @Override
    public void addTenantDataSource(Tenant tenant, DataSource dataSource) {
        ConcurrentMap<String, DataSource> map = tenantDataSources.asMap();
        if (map.containsKey(tenant.getTenantId())) {
            DataSource dataSource1 = tenantDataSources.get(tenant.getTenantId());
            if (dataSource1 instanceof HikariDataSource hikariDataSource) {
                hikariDataSource.close();
            }
        }
        tenantDataSources.put(tenant.getTenantId(), dataSource);
    }

    @Override
    public void runLiquibase(Tenant tenant, DataSource dataSource, LiquibaseProperties liquibaseProperties, ResourceLoader resourceLoader) throws LiquibaseException {
        SpringLiquibase liquibase = SpringLiquibaseUtils.create(dataSource, liquibaseProperties, resourceLoader);
        if (liquibaseProperties.getParameters() != null) {
            liquibaseProperties.getParameters().put("tenantName", tenant.getTenantId());
            liquibaseProperties.getParameters().put("db", tenant.getDb());
            liquibaseProperties.getParameters().put("username", tenant.getDb());
            liquibase.setChangeLogParameters(liquibaseProperties.getParameters());
        } else {
            HashMap<String, String> parameters = new HashMap<>();
            parameters.put("tenantName", tenant.getTenantId());
            parameters.put("db", tenant.getDb());
            parameters.put("username", tenant.getDb());
            liquibase.setChangeLogParameters(parameters);
        }
        liquibase.afterPropertiesSet();
    }

    @PreDestroy
    public void clearDataSourceCache() {
        ConcurrentMap<String, DataSource> map = tenantDataSources.asMap();
        map.forEach((key, value) -> {
            if (value instanceof HikariDataSource hikariDataSource) {
                hikariDataSource.close();
            }
        });
    }
}
