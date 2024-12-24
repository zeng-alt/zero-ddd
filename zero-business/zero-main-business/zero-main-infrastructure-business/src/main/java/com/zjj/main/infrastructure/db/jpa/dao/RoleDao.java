package com.zjj.main.infrastructure.db.jpa.dao;

import com.zjj.graphql.component.supper.BaseRepository;
import com.zjj.main.infrastructure.db.jpa.entity.Role;
import io.vavr.control.Option;
import org.springframework.graphql.data.GraphQlRepository;

@GraphQlRepository
public interface RoleDao extends BaseRepository<Role, Long> {
    Option<Role> findById(Long roleId);

    Option<Role> findByRoleKey(String roleKey);

    void save(Role role);
}