package com.zjj.tenant.infrastructure.db.jpa;

import com.zjj.graphql.component.supper.BaseRepository;
import com.zjj.tenant.domain.menu.IMenuResource;
import com.zjj.tenant.domain.menu.MenuResource;
import io.vavr.collection.List;
import io.vavr.control.Option;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.graphql.data.GraphQlRepository;

import java.util.Collection;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月30日 21:11
 */
@GraphQlRepository
public interface MenuResourceDao extends BaseRepository<MenuResource, Long> {

    List<MenuResource> findAllByIdIn(Collection<Long> ids);

    Option<IMenuResource> findById(Long id);

    MenuResource save(MenuResource menuResource);


//    @Modifying
//    @Query(value = "delete from MenuResource m where m.id=:id")
    void deleteById(Long id);

    void delete(MenuResource menuResource);
}
