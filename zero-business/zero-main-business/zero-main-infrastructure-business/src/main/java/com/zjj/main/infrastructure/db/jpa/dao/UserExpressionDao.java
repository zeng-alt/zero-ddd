package com.zjj.main.infrastructure.db.jpa.dao;

import com.zjj.domain.component.BaseRepository;
import com.zjj.main.infrastructure.db.jpa.entity.UserExpression;
import org.springframework.graphql.data.GraphQlRepository;

@GraphQlRepository
public interface UserExpressionDao extends BaseRepository<UserExpression, Long> {
}