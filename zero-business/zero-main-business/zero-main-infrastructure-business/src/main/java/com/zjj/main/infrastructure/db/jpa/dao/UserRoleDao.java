package com.zjj.main.infrastructure.db.jpa.dao;

import com.zjj.domain.component.BaseRepository;
import com.zjj.main.infrastructure.db.jpa.entity.UserRole;
import io.vavr.control.Option;
import org.springframework.graphql.data.GraphQlRepository;

@GraphQlRepository
public interface UserRoleDao extends BaseRepository<UserRole, Long> {

    public Option<UserRole> findByUser_IdAndRole_Id(Long userId, Long roleId);
}