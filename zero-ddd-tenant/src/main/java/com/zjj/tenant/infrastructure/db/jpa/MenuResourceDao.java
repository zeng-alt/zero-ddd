package com.zjj.tenant.infrastructure.db.jpa;

import com.zjj.graphql.component.supper.BaseRepository;
import com.zjj.tenant.infrastructure.db.entity.MenuResourceEntity;
import io.vavr.collection.List;
import io.vavr.control.Option;
import org.springframework.graphql.data.GraphQlRepository;

import java.util.Collection;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月30日 21:11
 */
@GraphQlRepository
public interface MenuResourceDao extends BaseRepository<MenuResourceEntity, Long> {

    List<MenuResourceEntity> findAllByIdIn(Collection<Long> ids);

    Option<MenuResourceEntity> findById(Long id);

    MenuResourceEntity save(MenuResourceEntity menuResource);


//    @Modifying
//    @Query(value = "delete from MenuResource m where m.id=:id")
    void deleteById(Long id);

    void delete(MenuResourceEntity menuResource);
}
