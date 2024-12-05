package com.zjj.main.infrastructure.db.jpa.dao;

import com.zjj.graphql.component.supper.BaseRepository;
import com.zjj.main.infrastructure.db.jpa.entity.RolePrecondition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePreconditionDao extends BaseRepository<RolePrecondition, Long> {
}