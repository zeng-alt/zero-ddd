package com.zjj.main.infrastructure.db.jpa.entity;

import com.zjj.domain.component.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月15日 10:46
 */
@Getter
@Setter
@Entity
@Table(name = "main_user_group")
public class UserGroup extends BaseEntity<Long> {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "userGroup", orphanRemoval = true)
    private Set<UserGroupRole> userGroupRoles = new LinkedHashSet<>();

    @OneToMany(mappedBy = "userGroup", orphanRemoval = true)
    private Set<UserGroupUser> userGroupUsers = new LinkedHashSet<>();

}
