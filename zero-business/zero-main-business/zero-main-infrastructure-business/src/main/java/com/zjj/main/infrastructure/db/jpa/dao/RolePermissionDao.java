package com.zjj.main.infrastructure.db.jpa.dao;

import com.zjj.domain.component.BaseRepository;
import com.zjj.main.infrastructure.db.jpa.entity.RolePermission;
import org.springframework.graphql.data.GraphQlRepository;

@GraphQlRepository
public interface RolePermissionDao extends BaseRepository<RolePermission, Long> {
}