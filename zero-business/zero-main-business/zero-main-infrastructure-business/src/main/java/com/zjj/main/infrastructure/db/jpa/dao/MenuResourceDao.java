package com.zjj.main.infrastructure.db.jpa.dao;

import com.zjj.domain.component.BaseRepository;
import com.zjj.main.infrastructure.db.jpa.entity.MenuResource;
import org.springframework.graphql.data.GraphQlRepository;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月03日 15:36
 */
@GraphQlRepository
public interface MenuResourceDao extends BaseRepository<MenuResource, Long> {

//    List<MenuResource> findAllByParentMenu_Id(Long id);

    List<MenuResource> findByParentMenuIsNull();

//    List<MenuResource> findAllByParentMenu_Id(Long id);
}
