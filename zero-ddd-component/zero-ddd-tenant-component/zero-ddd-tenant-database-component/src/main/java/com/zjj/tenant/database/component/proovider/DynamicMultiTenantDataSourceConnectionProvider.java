package com.zjj.tenant.database.component.proovider;


import com.zjj.tenant.management.component.service.TenantConnectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.initialization.qual.Initialized;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.UnknownKeyFor;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;

import java.io.Serial;
import java.sql.Connection;
import java.sql.SQLException;

@RequiredArgsConstructor
@Slf4j
public class DynamicMultiTenantDataSourceConnectionProvider<K>
        implements MultiTenantConnectionProvider<K> {

    @Serial
    private static final long serialVersionUID = -460277105706399638L;

    private final TenantConnectionService<K> tenantConnectionService;



    @Override
    public Connection getAnyConnection() throws SQLException {
        return tenantConnectionService.getConnection();
    }


    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }


    @Override
    public Connection getConnection(K tenantIdentifier) throws SQLException {
        return tenantConnectionService.getConnection(tenantIdentifier);
    }


    @Override
    public void releaseConnection(K tenantIdentifier, Connection connection) throws SQLException {
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