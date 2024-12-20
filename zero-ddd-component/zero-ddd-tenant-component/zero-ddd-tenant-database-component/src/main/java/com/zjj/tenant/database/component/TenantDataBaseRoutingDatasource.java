package com.zjj.tenant.database.component;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月04日 11:42
 */
public class TenantDataBaseRoutingDatasource extends AbstractRoutingDataSource {

    private final CurrentTenantIdentifierResolver tenantIdentifierResolver;


    public TenantDataBaseRoutingDatasource(CurrentTenantIdentifierResolver tenantIdentifierResolver, DataSource dataSource) {
        this.tenantIdentifierResolver = tenantIdentifierResolver;
        this.setDefaultTargetDataSource(dataSource);
        this.setTargetDataSources();

    }


    @Override
    protected Object determineCurrentLookupKey() {
        return tenantIdentifierResolver.resolveCurrentTenantIdentifier();
    }
}
