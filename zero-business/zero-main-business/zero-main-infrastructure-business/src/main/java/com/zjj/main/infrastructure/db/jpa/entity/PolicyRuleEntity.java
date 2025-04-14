package com.zjj.main.infrastructure.db.jpa.entity;

import com.zjj.domain.component.BaseEntity;
import com.zjj.domain.component.TenantAuditable;
import com.zjj.main.infrastructure.db.jpa.converter.ExpressionConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.TenantId;
import org.springframework.expression.Expression;
import org.springframework.lang.Nullable;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月15日 21:43
 */
@Getter
@Setter
@Entity
@Table(name = "main_policy_rule")
public class PolicyRuleEntity extends BaseEntity<Long> implements TenantAuditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "resource_id")
    private Resource resource;

    private String name;
    private String description;

    private Boolean preAuth;

    /*
     * Boolean SpEL expression, if evaluated to true, then access granted.
     */
//    @Convert(converter = ExpressionConverter.class)
    private String condition;

    @TenantId
    @Nullable
    private String tenantBy;
}
