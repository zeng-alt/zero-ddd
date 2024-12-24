package com.zjj.tenant.database.component;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.initialization.qual.Initialized;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.UnknownKeyFor;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.sql.Connection;
import java.sql.SQLException;

@RequiredArgsConstructor
@Slf4j
@Component
public class DynamicDataSourceBasedMultiTenantConnectionProvider
        implements MultiTenantConnectionProvider<String> {

    @Serial
    private static final long serialVersionUID = -460277105706399638L;

    private final TenantDataBaseRoutingDatasource dataSource;



    @Override
    public Connection getAnyConnection() throws SQLException {
        return dataSource.getConnection();
    }


    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }


    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        return dataSource.getConnection(tenantIdentifier);
    }


    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }


    @Override
    public @UnknownKeyFor @NonNull @Initialized boolean isUnwrappableAs(@UnknownKeyFor @NonNull @Initialized Class<@UnknownKeyFor @NonNull @Initialized ?> unwrapType) {
        return false;
    }


    @Override
    public <T> T unwrap(@UnknownKeyFor @NonNull @Initialized Class<T> unwrapType) {
        throw new UnsupportedOperationException("Can't unwrap this.");
    }

}