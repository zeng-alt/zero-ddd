package com.zjj.main.infrastructure.db.jpa.dao;

import com.zjj.domain.component.BaseRepository;
import com.zjj.main.infrastructure.db.jpa.entity.MenuResource;
import com.zjj.main.infrastructure.db.jpa.entity.Resource;
import org.springframework.graphql.data.GraphQlRepository;

import java.util.List;

@GraphQlRepository
public interface ResourceDao extends BaseRepository<Resource, Long> {

    List<Resource> findAll();
}