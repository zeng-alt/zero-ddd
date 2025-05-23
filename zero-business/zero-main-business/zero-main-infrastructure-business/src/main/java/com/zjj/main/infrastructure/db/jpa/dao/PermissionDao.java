package com.zjj.main.infrastructure.db.jpa.dao;

import com.zjj.domain.component.BaseRepository;
import com.zjj.main.infrastructure.db.jpa.entity.Permission;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.graphql.data.GraphQlRepository;

import java.util.stream.Stream;

@GraphQlRepository
public interface PermissionDao extends BaseRepository<Permission, Long> {

    Stream<Permission> findAllByIdIn(Iterable<Long> ids);

    Stream<Permission> findAll();
}