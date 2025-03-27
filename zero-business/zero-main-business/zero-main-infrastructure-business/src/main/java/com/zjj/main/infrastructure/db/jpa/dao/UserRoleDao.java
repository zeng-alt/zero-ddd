package com.zjj.main.infrastructure.db.jpa.dao;

import com.zjj.domain.component.BaseRepository;
import com.zjj.main.infrastructure.db.jpa.entity.UserRole;
import org.springframework.graphql.data.GraphQlRepository;

@GraphQlRepository
public interface UserRoleDao extends BaseRepository<UserRole, Long> {
}