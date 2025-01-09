package com.zjj.tenant.infrastructure.repository;

import com.zjj.domain.component.DomainBeanHelper;
import com.zjj.tenant.domain.menu.MenuResourceAggregate;
import com.zjj.tenant.infrastructure.db.entity.MenuResourceEntity;
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


    public List<MenuResourceAggregate> findAllById(Collection<Long> ids) {
//        return List.ofAll(menuResourceDao.findAllById(ids));
        return menuResourceDao.findAllByIdIn(ids).map(m -> DomainBeanHelper.copyToDomain(m, MenuResource.class, MenuResource.MenuResourceId.class));
    }

    public Option<MenuResourceAggregate> findById(Long id) {
        return menuResourceDao.findById(id).map(m -> DomainBeanHelper.copyToDomain(m, MenuResource.class, MenuResource.MenuResourceId.class));
    }

    @Override
    public MenuResourceAggregate save(MenuResourceAggregate iMenuResource) {
        if (iMenuResource instanceof MenuResource menuResource) {
            MenuResourceEntity save = menuResourceDao.save(DomainBeanHelper.copyToDomain(menuResource, MenuResourceEntity.class));
            return DomainBeanHelper.copyToDomain(save, MenuResource.class, MenuResource.MenuResourceId.class);
        }
        throw new IllegalArgumentException("iMenuResource is not instance of MenuResource");
    }

    @Override
    public void remove(MenuResourceAggregate iMenuResource) {

        if (iMenuResource instanceof MenuResource menuResource) {
            menuResourceDao.delete(DomainBeanHelper.copyToDomain(menuResource, MenuResourceEntity.class));
//            menuResourceDao.deleteById(menuResource.getId());
            return;
        }
        throw new IllegalArgumentException("iMenuResource is not instance of MenuResource");

    }
}
