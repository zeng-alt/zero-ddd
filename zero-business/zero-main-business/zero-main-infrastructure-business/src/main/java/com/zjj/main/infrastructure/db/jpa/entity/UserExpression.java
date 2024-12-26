package com.zjj.main.infrastructure.db.jpa.entity;

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
 * @crateTime 2024年11月15日 21:48
 */
@Getter
@Setter
@Entity
@Table(name = "main_user_expression")
public class UserExpression extends BaseEntity<Long> implements TenantAuditable<String> {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_resource_id")
    private UserResource userResource;

    @TenantId
    @Nullable
    private String tenantBy;
}
