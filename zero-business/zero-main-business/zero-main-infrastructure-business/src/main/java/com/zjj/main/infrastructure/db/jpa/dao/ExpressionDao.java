package com.zjj.main.infrastructure.db.jpa.dao;

import com.zjj.graphql.component.supper.BaseRepository;
import com.zjj.main.infrastructure.db.jpa.entity.Expression;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpressionDao extends BaseRepository<Expression, Long> {
}