package com.zjj.main.infrastructure.db.jpa.dao;

import com.zjj.domain.component.BaseRepository;
import com.zjj.main.infrastructure.db.jpa.entity.PermissionRuleEntity;
import org.springframework.graphql.data.GraphQlRepository;

import java.util.stream.Stream;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月09日 15:38
 */
@GraphQlRepository
public interface PermissionRuleEntityDao extends BaseRepository<PermissionRuleEntity, Long> {

//    public Stream<PermissionRuleEntity> findAll();

    public Stream<PermissionRuleEntity> findAllByRulesNotEmpty();
}
