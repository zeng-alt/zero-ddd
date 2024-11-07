package com.zjj.tenant.domain.menu;

import io.vavr.collection.List;
import io.vavr.control.Option;

import java.util.Collection;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月04日 10:20
 */
public interface MenuResourceRepository {

    List<MenuResource> findAllById(Collection<Long> ids);

    Option<IMenuResource> findById(Long id);

    IMenuResource save(IMenuResource iMenuResource);
}
