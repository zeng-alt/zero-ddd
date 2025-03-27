package com.zjj.main.infrastructure.db.jpa.entity;

import com.zjj.domain.component.BaseEntity;
import com.zjj.domain.component.TenantAuditable;
import com.zjj.graphql.component.annotations.MutationEntity;
import com.zjj.main.infrastructure.db.jpa.mutation.UserEntitySaveHandler;
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
 * @crateTime 2024年11月15日 21:21
 */
@Getter
@Setter
@Entity
@SQLDelete(sql = "update main_user set deleted = 1 where id = ?")
@SQLRestriction(value = "deleted = 0")
@Table(name = "MAIN_USER")
@MutationEntity(saveHandlers = {UserEntitySaveHandler.class})
public class User extends BaseEntity<Long> implements TenantAuditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Set<UserRole> userRoles = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Set<UserGroupUser> userGroupUsers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Set<UserResource> userResources = new LinkedHashSet<>();

    /**
     * 用户账号
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickName;


    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 用户性别
     */
    private String gender;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;

    /**
     * 帐号状态（0正常 1停用）
     */
    private String status = "0";

    /**
     * 逻辑删除标志位，0 未删除，1 已删除
     */
    private Integer deleted = 0;

    @TenantId
    @Nullable
    private String tenantBy;

    @PreRemove
    public void deleteUser() {
        this.deleted = 1;
    }

}
