package com.zjj.autoconfigure.component.tenant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
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

    /** TenantDataSourceCapacity */
    private DataSourceCache dataSourceCache = new DataSourceCache();
    /** SpringLiquibase */
    private DatabasePattern databasePattern = new DatabasePattern();
    private SchemaPattern schemaPattern = new SchemaPattern();
    private String master = "master";
    private Database database;

    /** EnableJpaRepositories */
    private RepositoryPackages repository = new RepositoryPackages();
    private EntityPackages entity = new EntityPackages();

    /** filter */
    private String tenantToken = "X-TENANT-ID";


    @Data
    public static class EntityPackages {
        String[] packages = {"com.zjj.tenant.service.component.entity"};
    }

    @Data
    public static class RepositoryPackages {
        String packages =  "com.zjj.tenant.service.component.repository";
    }

    public enum Database {

        DEFAULT,

        DB2,

        DERBY,

        /** @since 2.5.5 */
        H2,

        /** @since 5.1 */
        HANA,

        HSQL,

        INFORMIX,

        MYSQL,

        ORACLE,

        POSTGRESQL,

        SQL_SERVER,

        SYBASE

    }

    @Data
    public static class DataSourceCache {
        private Integer maximumSize = 100;
        private Integer expireAfterAccess = 10;
    }


    @Data
    public static class DatabasePattern {
        private String secret;
        private String salt;
        private String urlPrefix;
    }

    @Data
    public static class SchemaPattern {
        private String schema;
    }
}
