package com.zjj.main.infrastructure.db.jpa.dao;

import com.zjj.domain.component.BaseRepository;
import com.zjj.main.infrastructure.db.jpa.entity.HttpResource;
import org.springframework.graphql.data.GraphQlRepository;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年05月25日 18:53
 */
@GraphQlRepository
public interface HttpResourceDao extends BaseRepository<HttpResource, Long> {

    List<HttpResource> findAll();

    List<HttpResource> findAllByMenuIdIn(List<Long> menuIds);
}
