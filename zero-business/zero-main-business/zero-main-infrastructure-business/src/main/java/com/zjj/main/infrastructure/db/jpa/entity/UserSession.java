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
 * @crateTime 2024年11月15日 21:49
 */
@Getter
@Setter
@Entity
@Table(name = "main_user_session")
public class UserSession extends BaseEntity<Long> implements TenantAuditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @TenantId
    @Nullable
    private String tenantBy;
}
