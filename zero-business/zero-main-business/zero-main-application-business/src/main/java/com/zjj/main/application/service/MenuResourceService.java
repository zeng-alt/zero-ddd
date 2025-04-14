package com.zjj.main.application.service;

import com.zjj.main.infrastructure.db.jpa.entity.MenuResource;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月09日 16:43
 */
public interface MenuResourceService {

    public Iterable<MenuResource> tree(String username, String roleCode);

    public Iterable<MenuResource> tree();

    Iterable<MenuResource> button(Long id);
}
