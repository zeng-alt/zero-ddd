package com.zjj.tenant.database.component;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.RemovalListener;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.MultiTenancySettings;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author zengJiaJun
 * @crateTime 2024年12月03日 20:44
 * @version 1.0
 */
@Slf4j
public class DynamicDataSourceBasedMultiTenantConnectionProvider extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl<String> implements HibernatePropertiesCustomizer {

    private static final String TENANT_POOL_NAME_SUFFIX = "DataSource";
    private LoadingCache<String, DataSource> tenantDataSources;
    private  MasterTenantRepository masterTenantRepository;
//    @Autowired
//    @Qualifier("masterDataSourceProperties")
    private DataSourceProperties dataSourceProperties;

    @PostConstruct
    private void createCache() {
        tenantDataSources = Caffeine
                .from("")
                .removalListener(
                    (RemovalListener<String, DataSource>) (key, value, cause) -> {
                        if (value instanceof HikariDataSource ds) {
                            ds.close();
                            log.info("Closed datasource: {}", ds.getPoolName());
                        }
                    }
                ).build(key -> {
                    TenantDatabase tenant = masterTenantRepository.findByTenantId(key)
                            .orElseThrow(() -> new RuntimeException("No such tenant: " + key));
                    return createAndConfigureDataSource(tenant);
                });
    }

    private DataSource createAndConfigureDataSource(TenantDatabase tenant) {
        String decryptedPassword = null;

        HikariDataSource ds = dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();

        ds.setUsername(tenant.getDb());
        ds.setPassword(decryptedPassword);
        ds.setJdbcUrl(tenant.getUrl());

        ds.setPoolName(tenant.getTenantId() + TENANT_POOL_NAME_SUFFIX);

        log.info("Configured datasource: {}", ds.getPoolName());
        return ds;
    }


    @Override
    protected DataSource selectAnyDataSource() {
        return null;
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        DataSource dataSource = tenantDataSources.get(tenantIdentifier);
        if (dataSource == null) {
            throw new RuntimeException("Failed to load DataSource for tenant: " + tenantIdentifier);
        }
        return dataSource;
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(MultiTenancySettings.MULTI_TENANT_CONNECTION_PROVIDER, this);
    }
}
