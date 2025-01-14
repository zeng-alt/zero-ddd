package com.zjj.tenant.infrastructure.db.entity;

import com.zjj.domain.component.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年01月07日 21:20
 */
@Entity(name = "TenantDataSource")
@Table(name = "tenant_data_source")
@Getter
@Setter
public class TenantDataSourceEntity extends BaseEntity<Long> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String db;
    private String password;

    private String schema;
    private Boolean enabled;
    private String mode;
}
