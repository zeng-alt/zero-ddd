package com.zjj.tenant.domain.menu;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月05日 20:13
 */
public interface IMenuResource {

    public IMenuResource disable();

    IMenuResource enable();

    IMenuResource setParentMenu(IMenuResource parentMenu);

    IMenuResource remove();

    Long getId();
}
