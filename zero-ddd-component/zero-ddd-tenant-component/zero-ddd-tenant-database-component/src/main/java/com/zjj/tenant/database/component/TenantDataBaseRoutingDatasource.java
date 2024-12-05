package com.zjj.tenant.database.component;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 无法实现动态添加删除数据源，不使用
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月04日 11:42
 */
public class TenantDataBaseRoutingDatasource extends AbstractRoutingDataSource {

    private TenantIdentifierResolver tenantIdentifierResolver;

    @Override
    protected Object determineCurrentLookupKey() {
        return tenantIdentifierResolver.resolveCurrentTenantIdentifier();
    }
}
