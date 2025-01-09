package com.zjj.tenant.domain.tenant;

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
public class TenantDataSource implements Entity<Tenant, TenantDataSource.TenantDataSourceId> {


    private TenantDataSourceId id;

    private String db;
    private String password;

    private String schema;
    private Boolean enabled;


    @Value(staticConstructor = "of")
    public static class TenantDataSourceId implements Identifier {
        Long id;
    }
}
