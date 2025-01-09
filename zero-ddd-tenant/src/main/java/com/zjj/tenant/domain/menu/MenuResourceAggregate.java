package com.zjj.tenant.domain.menu;

import org.jmolecules.ddd.types.AggregateRoot;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月05日 20:13
 */
public interface MenuResourceAggregate extends AggregateRoot<MenuResource, MenuResource.MenuResourceId> {

    public MenuResourceAggregate disable();

    MenuResourceAggregate enable();

    MenuResourceAggregate setParentMenu(MenuResourceAggregate parentMenu);

    MenuResourceAggregate remove();
}
