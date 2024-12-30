package com.zjj.tenant.domain.tenant;

import com.zjj.domain.component.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月30日 21:48
 */
@Table(name = "tenant_data_source")
@Getter
@Setter
@Entity
public class TenantDataSource extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String db;
    private String password;

    private String schema;
    private Boolean enabled;
}
