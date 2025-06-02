package com.zjj.main.application.service;

import com.zjj.main.application.dto.MenuResourceDTO;
import com.zjj.main.infrastructure.db.jpa.entity.MenuResource;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月09日 16:43
 */
public interface MenuResourceService {

    public List<MenuResourceDTO> tree(String username, String roleCode);

    public List<MenuResourceDTO> tree();

    public List<MenuResourceDTO> treeMenu();

    public List<MenuResourceDTO> treeAll();

    Iterable<MenuResource> button(Long id);
}
