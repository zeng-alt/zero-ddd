package com.zjj.main.infrastructure.db.jpa.dao;

import com.zjj.domain.component.BaseRepository;
import com.zjj.main.infrastructure.db.jpa.entity.Role;
import io.vavr.control.Option;
import org.springframework.graphql.data.GraphQlRepository;

import java.util.stream.Stream;

@GraphQlRepository
public interface RoleDao extends BaseRepository<Role, Long> {
    Option<Role> findById(Long roleId);

    Option<Role> findByCode(String roleKey);

    Stream<Role> findAll();

    Role save(Role role);
}