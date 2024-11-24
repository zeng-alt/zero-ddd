package com.zjj.tenant.infrastructure.repository;

import com.zjj.tenant.domain.menu.IMenuResource;
import com.zjj.tenant.infrastructure.db.jpa.MenuResourceDao;
import com.zjj.tenant.domain.menu.MenuResource;
import com.zjj.tenant.domain.menu.MenuResourceRepository;
import io.vavr.collection.List;
import io.vavr.control.Option;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月04日 21:20
 */
@Component
public record MenuResourceRepositoryImpl(MenuResourceDao menuResourceDao) implements MenuResourceRepository {


    public List<MenuResource> findAllById(Collection<Long> ids) {
        return menuResourceDao.findAllByIdIn(ids);
    }

    public Option<IMenuResource> findById(Long id) {
        return menuResourceDao.findById(id);
    }

    @Override
    public IMenuResource save(IMenuResource iMenuResource) {

        if (iMenuResource instanceof MenuResource menuResource) {
            return menuResourceDao.save(menuResource);
        }

        throw new IllegalArgumentException("iMenuResource is not instance of MenuResource");
    }

    @Override
    public void remove(IMenuResource iMenuResource) {

        if (iMenuResource instanceof MenuResource menuResource) {
            menuResourceDao.delete(menuResource);
            return;
        }
        throw new IllegalArgumentException("iMenuResource is not instance of MenuResource");

    }
}
