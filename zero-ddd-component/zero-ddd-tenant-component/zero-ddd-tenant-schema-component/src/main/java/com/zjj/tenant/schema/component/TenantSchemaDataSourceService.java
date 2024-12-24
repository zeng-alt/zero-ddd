//package com.zjj.tenant.schema.component;
//
//import com.github.benmanes.caffeine.cache.Caffeine;
//import com.github.benmanes.caffeine.cache.LoadingCache;
//import com.zjj.autoconfigure.component.tenant.MultiTenancyProperties;
//import com.zjj.exchange.tenant.client.RemoteTenantClient;
//import com.zjj.exchange.tenant.domain.Tenant;
//import com.zjj.tenant.management.component.service.TenantCreationException;
//import com.zjj.tenant.management.component.service.TenantDataSourceService;
//import com.zjj.tenant.management.component.utils.SpringLiquibaseUtils;
//import jakarta.annotation.PostConstruct;
//import liquibase.exception.LiquibaseException;
//import liquibase.integration.spring.SpringLiquibase;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
//import org.springframework.core.io.ResourceLoader;
//
//import javax.sql.DataSource;
//import java.util.Collections;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author zengJiaJun
// * @version 1.0
// * @crateTime 2024年12月24日 21:03
// */
//@RequiredArgsConstructor
//public class TenantSchemaDataSourceService implements TenantDataSourceService {
//
//    private static final String VALID_SCHEMA_NAME_REGEXP = "[A-Za-z0-9_]*";
//    private final RemoteTenantClient remoteTenantClient;
//    private final MultiTenancyProperties tenancyProperties;
//    private LoadingCache<String, String> tenantSchemas;
//
//    @PostConstruct
//    private void createCache() {
//        Caffeine<Object, Object> tenantsCacheBuilder = Caffeine.newBuilder();
//        tenantsCacheBuilder.maximumSize(tenancyProperties.getDataSourceCache().getMaximumSize());
//        tenantsCacheBuilder.expireAfterAccess(tenancyProperties.getDataSourceCache().getExpireAfterAccess(), TimeUnit.MINUTES);
//        tenantSchemas = tenantsCacheBuilder.build(
//                tenantId -> {
//                    Tenant tenant = remoteTenantClient.findById(tenantId)
//                            .getOrElseThrow(() -> new RuntimeException("No such tenant: " + tenantId));
//                    return tenant.getSchema();
//                });
//    }
//
//    public String getSchema(String id) {
//        return tenantSchemas.get(id);
//    }
//
//    @Override
//    public void verify(Tenant tenant) {
//        if (tenant.getSchema() != null && !tenant.getSchema().matches(VALID_SCHEMA_NAME_REGEXP)) {
//            throw new TenantCreationException("Invalid schema name: " + tenant.getSchema());
//        }
//    }
//
//    @Override
//    public DataSource createDatasource(Tenant tenant) {
//        return null;
//    }
//
//    @Override
//    public void addTenantDataSource(Tenant tenant, DataSource dataSource) {
//        tenantSchemas.put(tenant.getTenantId(), tenant.getSchema());
//    }
//
//    @Override
//    public void runLiquibase(Tenant tenant, DataSource dataSource, LiquibaseProperties liquibaseProperties, ResourceLoader resourceLoader) throws LiquibaseException {
//        SpringLiquibase liquibase = SpringLiquibaseUtils.create(dataSource, liquibaseProperties, resourceLoader);
//        if (liquibaseProperties.getParameters() != null) {
//            liquibaseProperties.getParameters().put("schema", tenant.getSchema());
//            liquibase.setChangeLogParameters(liquibaseProperties.getParameters());
//        } else {
//            liquibase.setChangeLogParameters(Collections.singletonMap("schema", tenant.getSchema()));
//        }
//        liquibase.afterPropertiesSet();
//    }
//}
