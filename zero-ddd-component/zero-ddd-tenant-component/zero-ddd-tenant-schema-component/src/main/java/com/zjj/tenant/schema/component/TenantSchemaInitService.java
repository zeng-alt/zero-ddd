package com.zjj.tenant.schema.component;

import com.zjj.autoconfigure.component.tenant.MultiTenancyProperties;
import com.zjj.autoconfigure.component.tenant.Tenant;
import com.zjj.tenant.management.component.service.TenantInitDataSourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月23日 21:27
 */
@Slf4j
@RequiredArgsConstructor
public class TenantSchemaInitService implements TenantInitDataSourceService {

    private final MultiTenancyProperties multiTenancyProperties;

    @Override
    public void initDataSource(Tenant tenant, DataSource dataSource) {

        if (!StringUtils.hasText(tenant.getSchema())) {
            log.error("tenant.schema is empty");
            return;
        }

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        if (multiTenancyProperties.getDatabase().equals(MultiTenancyProperties.Database.POSTGRESQL)) {
            jdbcTemplate.execute((StatementCallback<Boolean>) stmt -> stmt.execute("CREATE SCHEMA IF NOT EXISTS \"" + tenant.getSchema() + "\""));
        }
        if (multiTenancyProperties.getDatabase().equals(MultiTenancyProperties.Database.H2)) {
            jdbcTemplate.execute((StatementCallback<Boolean>) stmt -> stmt.execute("CREATE SCHEMA IF NOT EXISTS \"" + tenant.getSchema() + "\""));
        }
    }
}
