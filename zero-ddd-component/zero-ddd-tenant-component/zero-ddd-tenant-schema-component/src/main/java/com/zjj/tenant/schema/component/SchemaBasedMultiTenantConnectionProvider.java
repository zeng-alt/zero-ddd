package com.zjj.tenant.schema.component;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.zjj.autoconfigure.component.tenant.MultiTenancyProperties;
import com.zjj.exchange.tenant.client.RemoteTenantClient;
import com.zjj.exchange.tenant.domain.Tenant;
import com.zjj.tenant.management.component.SpringLiquibase;
import com.zjj.tenant.management.component.service.TenantCreationException;
import com.zjj.tenant.management.component.service.TenantDataSourceService;
import com.zjj.tenant.management.component.utils.SpringLiquibaseUtils;
import jakarta.annotation.PostConstruct;
import liquibase.exception.LiquibaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.service.UnknownUnwrapTypeException;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月23日 21:31
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class SchemaBasedMultiTenantConnectionProvider implements MultiTenantConnectionProvider<String>, TenantDataSourceService {

    private static final String VALID_SCHEMA_NAME_REGEXP = "[A-Za-z0-9_]*";
    private final DataSource datasource;
    private final RemoteTenantClient remoteTenantClient;
    private final String master;
    private final MultiTenancyProperties tenancyProperties;

    private LoadingCache<String, String> tenantSchemas;


    public SchemaBasedMultiTenantConnectionProvider(DataSource datasource, MultiTenancyProperties tenancyProperties, RemoteTenantClient remoteTenantClient) {
        this.datasource = datasource;
        this.remoteTenantClient = remoteTenantClient;
        this.tenancyProperties = tenancyProperties;
        try(Connection connection = datasource.getConnection()) {
            this.master = connection.getSchema();
        } catch (SQLException e) {
            throw new TenantCreationException("在Schema模式下,获取主数据源的schema失败：" + e);
        }
    }

    @PostConstruct
    private void createCache() {
        Caffeine<Object, Object> tenantsCacheBuilder = Caffeine.newBuilder();
        tenantsCacheBuilder.maximumSize(tenancyProperties.getDataSourceCache().getMaximumSize());
        tenantsCacheBuilder.expireAfterAccess(tenancyProperties.getDataSourceCache().getExpireAfterAccess(), TimeUnit.MINUTES);
        tenantSchemas = tenantsCacheBuilder.build(
                tenantId -> {
                    Tenant tenant = remoteTenantClient.findById(tenantId)
                            .getOrElseThrow(() -> new RuntimeException("No such tenant: " + tenantId));
                    return tenant.getSchema();
                });
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        return datasource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        log.info("Get connection for tenant {}", tenantIdentifier);
        String tenantSchema = null;
        if ("master".equals(tenantIdentifier)) {
            tenantSchema = master;
        } else {
            tenantSchema = tenantSchemas.get(tenantIdentifier);
        }
        final Connection connection = getAnyConnection();
        connection.setSchema(tenantSchema.toUpperCase(Locale.ROOT));
        return connection;
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        log.info("Release connection for tenant {}", tenantIdentifier);
        connection.setSchema(master);
        releaseAnyConnection(connection);
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public boolean isUnwrappableAs(Class unwrapType) {
        return MultiTenantConnectionProvider.class.isAssignableFrom(unwrapType);
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        if ( MultiTenantConnectionProvider.class.isAssignableFrom(unwrapType) ) {
            return (T) this;
        } else {
            throw new UnknownUnwrapTypeException( unwrapType );
        }
    }

    @Override
    public void verify(Tenant tenant) {
        if (tenant.getSchema() != null && !tenant.getSchema().matches(VALID_SCHEMA_NAME_REGEXP)) {
            throw new TenantCreationException("Invalid schema name: " + tenant.getSchema());
        }
    }

    @Override
    public DataSource createDatasource(Tenant tenant) {
        return datasource;
    }

    @Override
    public void addTenantDataSource(Tenant tenant, DataSource dataSource) {
        tenantSchemas.put(tenant.getTenantId(), tenant.getSchema().toUpperCase());
    }

    @Override
    public void runLiquibase(Tenant tenant, DataSource dataSource, LiquibaseProperties liquibaseProperties, ResourceLoader resourceLoader) throws LiquibaseException {
        String schema = tenant.getSchema().toUpperCase();
        SpringLiquibase liquibase = SpringLiquibaseUtils.create(dataSource, liquibaseProperties, resourceLoader);
        if (liquibaseProperties.getParameters() != null) {
            liquibaseProperties.getParameters().put("schema", schema);
            liquibase.setChangeLogParameters(liquibaseProperties.getParameters());
        } else {
            liquibase.setChangeLogParameters(Collections.singletonMap("schema", schema));
        }
        liquibase.setLiquibaseSchema(schema);
        liquibase.setDefaultSchema(schema);
        liquibase.afterPropertiesSet();
    }

}
