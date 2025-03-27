package com.zjj.main.infrastructure.db.jpa.dao;

import com.zjj.domain.component.BaseRepository;
import com.zjj.main.infrastructure.db.jpa.entity.ParameterEntity;
import org.springframework.graphql.data.GraphQlRepository;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月25日 10:33
 */
@GraphQlRepository
public interface ParameterEntityDao extends BaseRepository<ParameterEntity, Long> {
}
