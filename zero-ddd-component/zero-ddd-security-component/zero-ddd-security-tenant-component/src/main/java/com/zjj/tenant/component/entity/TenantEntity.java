package com.zjj.tenant.component.entity;

import com.baomidou.dynamic.datasource.creator.DatasourceInitProperties;
import com.baomidou.dynamic.datasource.creator.atomikos.AtomikosConfig;
import com.baomidou.dynamic.datasource.creator.beecp.BeeCpConfig;
import com.baomidou.dynamic.datasource.creator.dbcp.Dbcp2Config;
import com.baomidou.dynamic.datasource.creator.druid.DruidConfig;
import com.baomidou.dynamic.datasource.creator.hikaricp.HikariCpConfig;
import com.zjj.tenant.component.entity.ITenantEntity;
import com.zjj.tenant.component.entity.TenantBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import javax.sql.DataSource;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月24日 11:35
 */
@Data
@Table(name = "tenant")
@Entity(name = "tenant")
public class TenantEntity extends TenantBaseEntity implements ITenantEntity {

    @Id
    @GeneratedValue()
    private Long id;
    private String poolName;
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private String jndiName;
    private Boolean seata = true;
    private Boolean p6spy = true;
    private Boolean lazy;
    private String publicKey;
    private Boolean enabled;

    @Override
    public boolean getEnabled() {
        return enabled;
    }
}
