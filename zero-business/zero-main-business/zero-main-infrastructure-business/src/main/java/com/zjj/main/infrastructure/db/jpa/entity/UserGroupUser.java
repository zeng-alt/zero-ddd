package com.zjj.main.infrastructure.db.jpa.entity;

import com.zjj.domain.component.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月15日 21:47
 */
@Getter
@Setter
@Entity
@Table(name = "main_user_group_user")
public class UserGroupUser extends BaseEntity<Long> {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "user_group_user_id")
    private UserGroup userGroup;

}
