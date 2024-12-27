package com.zjj.tenant.database.component;

import com.zjj.autoconfigure.component.tenant.MultiTenancyProperties;
import com.zjj.autoconfigure.component.tenant.Tenant;
import com.zjj.tenant.management.component.service.TenantInitDataSourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;

import java.sql.ResultSet;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月23日 21:22
 */
@RequiredArgsConstructor
public class TenantDatabaseInitService implements TenantInitDataSourceService {

    private final JdbcTemplate jdbcTemplate;
    private final MultiTenancyProperties multiTenancyProperties;

    @Override
    public void initDataSource(Tenant tenant) {
        if (multiTenancyProperties.getDatabase().equals(MultiTenancyProperties.Database.POSTGRESQL)) {
            // 判断database是否存在
            boolean existDatabase = jdbcTemplate.query("SELECT 1 FROM pg_database WHERE datname = '" + tenant.getDb() + "'", ResultSet::next);
            if (!existDatabase) {
                jdbcTemplate.execute((StatementCallback<Boolean>) stmt -> stmt.execute("CREATE DATABASE " + tenant.getDb()));
                jdbcTemplate.execute((StatementCallback<Boolean>) stmt -> stmt.execute("CREATE USER " + tenant.getDb() + " WITH ENCRYPTED PASSWORD '" + tenant.getPassword() + "'"));
                jdbcTemplate.execute((StatementCallback<Boolean>) stmt -> stmt.execute("GRANT ALL PRIVILEGES ON DATABASE " + tenant.getDb() + " TO " + tenant.getDb()));
            }
        }
    }
}
