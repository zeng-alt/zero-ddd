package com.zjj.main.infrastructure.db.jpa.dao;

import com.zjj.domain.component.BaseRepository;
import com.zjj.main.infrastructure.db.jpa.entity.Permission;
import com.zjj.main.infrastructure.db.jpa.entity.Role;
import com.zjj.main.infrastructure.db.jpa.entity.RolePermission;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.graphql.data.GraphQlRepository;

import java.util.List;
import java.util.stream.Stream;

@GraphQlRepository
public interface RolePermissionDao extends BaseRepository<RolePermission, Long> {


    @Modifying
    @Query("DELETE FROM RolePermission rp WHERE rp.role = :role AND rp.permission IN :permissions")
    void deleteByRoleAndPermissions(@Param("role") Role role, @Param("permissions") List<Permission> permissions);


    void saveAll(Iterable<RolePermission> entities);

    void deleteAll(Iterable<RolePermission> entities);
}