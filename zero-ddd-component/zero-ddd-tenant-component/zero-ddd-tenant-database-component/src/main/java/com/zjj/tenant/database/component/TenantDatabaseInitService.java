package com.zjj.tenant.database.component;

import com.zjj.autoconfigure.component.tenant.MultiTenancyProperties;
import com.zjj.exchange.tenant.domain.Tenant;
import com.zjj.tenant.management.component.service.TenantInitDataSourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;

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
            jdbcTemplate.execute((StatementCallback<Boolean>) stmt -> stmt.execute("CREATE DATABASE " + tenant.getDb()));
            jdbcTemplate.execute((StatementCallback<Boolean>) stmt -> stmt.execute("CREATE USER " + tenant.getDb() + " WITH ENCRYPTED PASSWORD '" + tenant.getPassword() + "'"));
            jdbcTemplate.execute((StatementCallback<Boolean>) stmt -> stmt.execute("GRANT ALL PRIVILEGES ON DATABASE " + tenant.getDb() + " TO " + tenant.getDb()));
        }
    }
}
