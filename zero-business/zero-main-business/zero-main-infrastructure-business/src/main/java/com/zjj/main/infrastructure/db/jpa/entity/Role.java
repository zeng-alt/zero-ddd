package com.zjj.main.infrastructure.db.jpa.entity;

import com.zjj.domain.component.BaseEntity;
import com.zjj.domain.component.TenantAuditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.TenantId;
import org.springframework.lang.Nullable;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月15日 21:33
 */
@Getter
@Setter
@Entity
@SQLDelete(sql = "update main_role set deleted = 1 where id = ?")
@SQLRestriction(value = "deleted = 0")
@Table(name = "main_role")
public class Role extends BaseEntity<Long> implements TenantAuditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "role")
    private Set<UserRole> userRoles = new LinkedHashSet<>();

    @OneToMany(mappedBy = "role")
    private Set<RolePermission> rolePermissions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "role")
    private Set<UserGroupRole> userGroupRoles = new LinkedHashSet<>();

    @OneToMany(mappedBy = "role")
    private Set<RoleExclusive> roleExclusives = new LinkedHashSet<>();

    @OneToMany(mappedBy = "role")
    private Set<RolePrecondition> rolePreconditions = new LinkedHashSet<>();

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色权限
     */
    private String code;

    /**
     * 角色排序
     */
    private Integer roleSort;


    /**
     * 角色状态（0正常 1停用）
     */
    private Boolean enable;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private Integer deleted;

    @TenantId
    @Nullable
    private String tenantBy;

}
