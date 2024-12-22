package com.zjj.tenant.management.component.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.stereotype.Component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月20日 10:25
 */
@Data
@Component
@ConfigurationProperties("multi-tenancy")
public class MultiTenancyProperties {

    private DataSourceCache dataSourceCache = new DataSourceCache();
    private String urlPrefix;
    private Database database;
    private String secret;
    private String salt;
//    private TenantDataSource master = new TenantDataSource();
//    private TenantDataSource tenant = new TenantDataSource();
//    private Encryption encryption = new Encryption();
    @Data
    public static class DataSourceCache {
        private Integer maximumSize = 100;
        private Integer expireAfterAccess = 10;
    }

    @Data
    public static class TenantDataSource {
        private DataSourceProperties datasource;
        private LiquibaseProperties liquibase;
    }

    @Data
    public static class Encryption {
        private String secret;
        private String salt;
    }


}
