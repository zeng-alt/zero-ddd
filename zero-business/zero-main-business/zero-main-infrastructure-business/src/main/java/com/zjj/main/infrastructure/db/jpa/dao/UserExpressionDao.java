package com.zjj.main.infrastructure.db.jpa.dao;

import com.zjj.graphql.component.supper.BaseRepository;
import com.zjj.main.infrastructure.db.jpa.entity.UserExpression;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserExpressionDao extends BaseRepository<UserExpression, Long> {
}