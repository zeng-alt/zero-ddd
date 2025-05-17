package com.zjj.main.infrastructure.db.jpa.dao;

import com.zjj.domain.component.BaseRepository;
import com.zjj.main.infrastructure.db.jpa.entity.DictType;
import org.springframework.graphql.data.GraphQlRepository;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年05月17日 10:22
 */
@GraphQlRepository
public interface DictTypeDao extends BaseRepository<DictType, Long> {
}
