package com.zjj.tenant.management.component.service;

import com.zaxxer.hikari.HikariDataSource;
import com.zjj.exchange.tenant.domain.Tenant;
import com.zjj.tenant.management.component.config.MultiTenancyProperties;
import com.zjj.tenant.management.component.utils.SpringLiquibaseUtils;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author zengJiaJun
 * @crateTime 2024年12月22日 16:30
 * @version 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class TenantManagementServiceImpl implements TenantManagementService {

    private static final String VALID_DATABASE_NAME_REGEXP = "\\w*";

    private final MultiTenancyProperties multiTenancyProperties;
    private final JdbcTemplate jdbcTemplate;
    private final LiquibaseProperties liquibaseProperties;
    private final ResourceLoader resourceLoader;
    private final TenantDatabaseService tenantDatabaseService;
    private final DataSourceProperties dataSourceProperties;

    @Override
    public void createTenant(String tenantId, String db, String password) {
        // Verify db string to prevent SQL injection
        if (!db.matches(VALID_DATABASE_NAME_REGEXP)) {
            throw new TenantCreationException("Invalid db name: " + db);
        }

        DataSource dataSource = null;

        try {
            dataSource = this.createDatabase(tenantId, db, password);
        } catch (DataAccessException e) {
            throw new TenantCreationException("Error when creating db: " + db, e);
        }

        if (dataSource != null) {
            try {
                runLiquibase(dataSource);
            } catch (LiquibaseException e) {
                throw new TenantCreationException("Error when populating db: ", e);
            }
        }

        tenantDatabaseService.addTenantDatabase(tenantId, dataSource);
    }

    private DataSource createDatabase(String tenantId, String db, String password) {
        if (!multiTenancyProperties.getDatabase().equals(Database.H2)) {
            jdbcTemplate.execute((StatementCallback<Boolean>) stmt -> stmt.execute("CREATE DATABASE " + db));
            jdbcTemplate.execute((StatementCallback<Boolean>) stmt -> stmt.execute("CREATE USER " + db + " WITH ENCRYPTED PASSWORD '" + password + "'"));
            jdbcTemplate.execute((StatementCallback<Boolean>) stmt -> stmt.execute("GRANT ALL PRIVILEGES ON DATABASE " + db + " TO " + db));
        }
        HikariDataSource ds = dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();

        ds.setUsername(db);
        ds.setPassword(password);
        ds.setJdbcUrl(multiTenancyProperties.getUrlPrefix() + db);

        ds.setPoolName(tenantId);

        log.info("Configured datasource: {}", ds.getPoolName());
        return ds;
    }

    private void runLiquibase(DataSource dataSource) throws LiquibaseException {
        SpringLiquibase liquibase = SpringLiquibaseUtils.create(dataSource, liquibaseProperties, resourceLoader);
        liquibase.afterPropertiesSet();
    }
}
