package com.zjj.tenant.infrastructure.db.entity;

import com.zjj.domain.component.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotEmpty(message = "数据库名称不能为空")
    private String db;
    @NotEmpty(message = "数据库密码不能为空")
    private String password;
//    @NotEmpty(message = "数据库用户名不能为空")
    private String schema;
    @NotNull(message = "数据库连接地址不能为空")
    private Boolean enabled;
    @NotEmpty(message = "数据库连接模式不能为空")
    private String mode;
}
