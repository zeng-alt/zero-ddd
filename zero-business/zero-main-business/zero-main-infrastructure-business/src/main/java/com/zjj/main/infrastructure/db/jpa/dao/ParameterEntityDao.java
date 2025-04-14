package com.zjj.main.infrastructure.db.jpa.dao;

import com.zjj.domain.component.BaseRepository;
import com.zjj.main.infrastructure.db.jpa.entity.ParameterEntity;
import io.vavr.control.Option;
import org.springframework.graphql.data.GraphQlRepository;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月25日 10:33
 */
@GraphQlRepository
public interface ParameterEntityDao extends BaseRepository<ParameterEntity, Long> {

    Option<ParameterEntity> findByParameterKey(String parameterKey);

    List<ParameterEntity> findAll();
}
