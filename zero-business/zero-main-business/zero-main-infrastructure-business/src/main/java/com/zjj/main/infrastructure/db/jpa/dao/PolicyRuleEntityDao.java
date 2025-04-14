package com.zjj.main.infrastructure.db.jpa.dao;

import com.zjj.domain.component.BaseRepository;
import com.zjj.main.infrastructure.db.jpa.entity.PolicyRuleEntity;
import org.springframework.graphql.data.GraphQlRepository;

import java.util.stream.Stream;

@GraphQlRepository
public interface PolicyRuleEntityDao extends BaseRepository<PolicyRuleEntity, Long> {

    Stream<PolicyRuleEntity> findAll();
}