package com.zjj.main.infrastructure.db.jpa.entity;

import com.zjj.domain.component.BaseEntity;
import com.zjj.domain.component.TenantAuditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.TenantId;
import org.springframework.lang.Nullable;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月15日 21:44
 */
@Getter
@Setter
@Entity
@Table(name = "main_user_resource")
public class UserResource extends BaseEntity<Long> implements TenantAuditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "resource_id")
    private MenuResource resource;

    @OneToMany(mappedBy = "userResource", orphanRemoval = true)
    private Set<UserExpression> userExpressions = new LinkedHashSet<>();


    @TenantId
    @Nullable
    private String tenantBy;
}
