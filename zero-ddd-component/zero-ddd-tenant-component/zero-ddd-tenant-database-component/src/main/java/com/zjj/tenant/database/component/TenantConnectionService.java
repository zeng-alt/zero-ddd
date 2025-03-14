package com.zjj.tenant.database.component;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月05日 21:30
 */
public interface TenantConnectionService<K> {

    public Connection getConnection() throws SQLException;

    public Connection getConnection(K tenantIdentifier) throws SQLException;

}
