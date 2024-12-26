package com.zjj.main.infrastructure.db.jpa.entity;

import com.zjj.domain.component.BaseEntity;
import com.zjj.domain.component.TenantAuditable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @GeneratedValue
    private Long id;


    @TenantId
    @Nullable
    private String tenantBy;
}
