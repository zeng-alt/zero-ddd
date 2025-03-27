package com.zjj.main.infrastructure.db.jpa.entity;

import com.zjj.core.component.api.Parent;
import com.zjj.domain.component.BaseEntity;
import com.zjj.domain.component.TenantAuditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.TenantId;
import org.springframework.lang.Nullable;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月25日 10:28
 */
@Getter
@Setter
@Entity(name = "Parameter")
@Table(name = "main_parameter")
public class ParameterEntity extends BaseEntity<Long> implements TenantAuditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String parameterName;
    private String parameterKey;
    private String parameterValue;
    private String parameterType;
    private String remark;

    @TenantId
    @Nullable
    private String tenantBy;
}
