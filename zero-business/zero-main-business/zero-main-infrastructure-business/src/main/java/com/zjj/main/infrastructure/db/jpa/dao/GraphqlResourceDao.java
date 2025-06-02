package com.zjj.main.infrastructure.db.jpa.dao;

import com.zjj.domain.component.BaseRepository;
import com.zjj.main.infrastructure.db.jpa.entity.GraphqlResourceEntity;
import com.zjj.main.infrastructure.db.jpa.entity.HttpResource;
import org.springframework.graphql.data.GraphQlRepository;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月03日 15:36
 */
@GraphQlRepository
public interface GraphqlResourceDao extends BaseRepository<GraphqlResourceEntity, Long> {

    Stream<GraphqlResourceEntity> findAllByUri(String uri);

    List<GraphqlResourceEntity> findAll();

    List<GraphqlResourceEntity> findAllByMenuIdIn(List<Long> menuIds);

    void saveAll(Iterable<GraphqlResourceEntity> graphqlResourceList);
}
