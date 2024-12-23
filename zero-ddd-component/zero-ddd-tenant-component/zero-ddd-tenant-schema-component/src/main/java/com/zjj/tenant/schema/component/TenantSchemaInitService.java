package com.zjj.tenant.schema.component;

import com.zjj.autoconfigure.component.tenant.MultiTenancyProperties;
import com.zjj.exchange.tenant.domain.Tenant;
import com.zjj.tenant.management.component.service.TenantInitDataSourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.orm.jpa.vendor.Database;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月23日 21:27
 */
@RequiredArgsConstructor
public class TenantSchemaInitService implements TenantInitDataSourceService {

    private final JdbcTemplate jdbcTemplate;
    private final MultiTenancyProperties multiTenancyProperties;

    @Override
    public void initDataSource(Tenant tenant) {
        if (multiTenancyProperties.getDatabase().equals(Database.POSTGRESQL)) {
            jdbcTemplate.execute((StatementCallback<Boolean>) stmt -> stmt.execute("CREATE SCHEMA " + tenant.getSchema()));
        }
        if (multiTenancyProperties.getDatabase().equals(Database.H2)) {
            jdbcTemplate.execute((StatementCallback<Boolean>) stmt -> stmt.execute("CREATE SCHEMA IF NOT EXISTS " + tenant.getSchema()));
        }
    }
}
