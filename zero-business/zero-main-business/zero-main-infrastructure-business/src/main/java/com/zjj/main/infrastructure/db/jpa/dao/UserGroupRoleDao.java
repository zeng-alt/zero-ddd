package com.zjj.main.infrastructure.db.jpa.dao;

import com.zjj.graphql.component.supper.BaseRepository;
import com.zjj.main.infrastructure.db.jpa.entity.UserGroupRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.graphql.data.GraphQlRepository;

@GraphQlRepository
public interface UserGroupRoleDao extends BaseRepository<UserGroupRole, Long> {
}