package com.zjj.tenant.domain.tenant;

import com.zjj.autoconfigure.component.tenant.TenantMode;
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
public class TenantDataSource implements Entity<Tenant, Long> {

    private Long id;
    private String db;
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
