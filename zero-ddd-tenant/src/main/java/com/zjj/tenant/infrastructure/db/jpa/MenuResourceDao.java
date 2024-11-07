package com.zjj.tenant.infrastructure.db.jpa;

import com.zjj.graphql.component.supper.BaseRepository;
import com.zjj.tenant.domain.menu.IMenuResource;
import com.zjj.tenant.domain.menu.MenuResource;
import io.vavr.collection.List;
import org.springframework.graphql.data.GraphQlRepository;

import java.util.Collection;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月30日 21:11
 */
@GraphQlRepository
public interface MenuResourceDao extends BaseRepository<MenuResource, Long> {

    public List<MenuResource> findAllByIdIn(Collection<Long> ids);

    MenuResource save(MenuResource menuResource);
}
