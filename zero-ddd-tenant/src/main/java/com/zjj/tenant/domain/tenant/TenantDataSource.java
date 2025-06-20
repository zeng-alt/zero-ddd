package com.zjj.tenant.domain.tenant;

import com.zjj.autoconfigure.component.tenant.TenantMode;
import com.zjj.security.abac.component.annotation.Schema;
import com.zjj.security.abac.component.annotation.SchemaProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.jmolecules.ddd.types.Entity;
import org.jmolecules.ddd.types.Identifier;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月30日 21:48
 */
@Getter
@Setter
@Schema(name = "数据源")
public class TenantDataSource implements Entity<Tenant, Long> {
    @SchemaProperty("id")
    private Long id;
    @SchemaProperty("数据库名")
    private String db;
    @SchemaProperty("数据库名字")
    private String password;
    private String schema;
    private TenantMode mode;
    private Boolean enabled;


    @Value(staticConstructor = "of")
    public static class TenantDataSourceId implements Identifier {
        Long id;
    }


    /**
     * <blockquote><pre>
     * mode = COLUMN   [db schema column] 数据库中不能有相同的db and schema and column
     * mode = SCHEMA   [db schema] 数据库中不能有相同的db and schema
     * mode = DATABASE [db] 数据库中不能有相同的db
     * </pre></blockquote>
     *
     */
    public void valid() {
//        QTenantDataSourceEntity query = QTenantDataSourceEntity.tenantDataSourceEntity;
//        if (TenantMode.COLUMN.equals(mode)) {
//            query.db.eq(this.db);
//        }
        if (TenantMode.DATABASE.equals(mode)) {
            if (db == null || db.isEmpty()) {
                throw new IllegalArgumentException("数据库名称不能为空");
            }
            if (password == null || password.isEmpty()) {
                throw new IllegalArgumentException("数据库密码不能为空");
            }
        }
        if (TenantMode.SCHEMA.equals(mode)) {
            if (schema == null || schema.isEmpty()) {
                throw new IllegalArgumentException("数据库模式名称不能为空");
            }
        }
    }
}
