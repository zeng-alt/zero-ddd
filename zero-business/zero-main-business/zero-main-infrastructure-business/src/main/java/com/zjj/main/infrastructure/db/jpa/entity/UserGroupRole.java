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
 * @crateTime 2024年11月15日 21:47
 */
@Getter
@Setter
@Entity
@Table(name = "main_user_group_role")
public class UserGroupRole extends BaseEntity<Long> implements TenantAuditable<String> {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_group_id")
    private UserGroup userGroup;


    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @TenantId
    @Nullable
    private String tenantBy;
}
