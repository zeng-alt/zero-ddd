package com.zjj.main.infrastructure.db.jpa.dao;

import com.zjj.graphql.component.supper.BaseRepository;
import com.zjj.main.infrastructure.db.jpa.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionDao extends BaseRepository<Permission, Long> {
}