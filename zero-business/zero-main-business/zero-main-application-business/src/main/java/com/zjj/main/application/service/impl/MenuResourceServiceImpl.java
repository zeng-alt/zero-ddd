package com.zjj.main.application.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zjj.main.application.dto.MenuResourceDTO;
import com.zjj.main.application.service.MenuResourceService;
import com.zjj.main.infrastructure.db.jpa.dao.*;
import com.zjj.main.infrastructure.db.jpa.entity.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月09日 16:43
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MenuResourceServiceImpl implements MenuResourceService {

    private final MenuResourceDao menuResourceDao;
    private final RoleDao roleDao;
    private final HttpResourceDao httpResourceDao;
    private final GraphqlResourceDao graphqlResourceDao;

    // TODO 优化成一次sql查询
    @Transactional(readOnly = true)
    public List<MenuResourceDTO> tree(String username, String roleCode) {

        if ("superAdmin".equals(username)) {
            return this.tree();
        }
        List<Permission> fetch = roleDao.findByCode(roleCode).map(role -> role.getRolePermissions().stream().map(r -> r.getPermission()).toList()).getOrElseThrow(() -> new RuntimeException("角色不存在"));
        List<MenuResource> menuResources = fetch.stream().filter(p -> p.getClass().isAssignableFrom(MenuResource.class)).map(p -> (MenuResource) p).toList();
        Map<Long, List<HttpResource>> httpResourceMap = fetch.stream().filter(p -> p.getClass().isAssignableFrom(HttpResource.class)).map(h -> (HttpResource) h).collect(Collectors.groupingBy(h -> h.getMenuId() == null ? 0L : h.getMenuId()));
        Map<Long, List<GraphqlResourceEntity>> graphqlResourceMap = fetch.stream().filter(p -> p.getClass().isAssignableFrom(GraphqlResourceEntity.class)).map(h -> (GraphqlResourceEntity) h).collect(Collectors.groupingBy(h -> h.getMenuId() == null ? 0L : h.getMenuId()));;

        return this.toTree(getRootMenu(menuResources), httpResourceMap, graphqlResourceMap);
    }

    @Override
    public List<MenuResourceDTO> tree() {
        List<MenuResource> menuResources = menuResourceDao.findByParentMenuIsNull();

        Map<Long, List<HttpResource>> httpResourceMap = httpResourceDao.findAll().stream().collect(Collectors.groupingBy(h -> h.getMenuId() == null ? 0L : h.getMenuId()));
        Map<Long, List<GraphqlResourceEntity>> graphqlResourceMap = graphqlResourceDao.findAll().stream().collect(Collectors.groupingBy(h -> h.getMenuId() == null ? 0L : h.getMenuId()));
        return this.toTree(menuResources, httpResourceMap, graphqlResourceMap);
    }

    @Override
    public List<MenuResourceDTO> treeMenu() {
        return this.toTree(menuResourceDao.findByParentMenuIsNull());
    }

    @Override
    public List<MenuResourceDTO> treeAll() {
        List<MenuResource> menuResources = menuResourceDao.findByParentMenuIsNull();
        List<Long> menuIds = new ArrayList<>();
        getMenuIds(menuResources, menuIds);
        Map<Long, List<HttpResource>> httpResourceMap = httpResourceDao.findAllByMenuIdIn(menuIds).stream().collect(Collectors.groupingBy(h -> h.getMenuId() == null ? 0L : h.getMenuId()));
        Map<Long, List<GraphqlResourceEntity>> graphqlResourceMap = graphqlResourceDao.findAllByMenuIdIn(menuIds).stream().collect(Collectors.groupingBy(h -> h.getMenuId() == null ? 0L : h.getMenuId()));
        return this.toTree(menuResources, httpResourceMap, graphqlResourceMap);
    }

    public void getMenuIds(List<MenuResource> menuResources, List<Long> menuIds) {
        for (MenuResource menuResource : menuResources) {
            menuIds.add(menuResource.getId());
            getMenuIds(menuResource.getChildren(), menuIds);
        }
    }

    private List<MenuResourceDTO> toTree(List<MenuResource> menuResources) {
        List<MenuResourceDTO> result = new ArrayList<>();
        for (MenuResource menuResource : menuResources) {
            result.add(to(menuResource));
        }
        return result;
    }

    private MenuResourceDTO to(MenuResource menuResource) {
        if ( menuResource == null ) {
            return null;
        }
        MenuResourceDTO menuResourceDTO = new MenuResourceDTO();
        BeanUtils.copyProperties(menuResource, menuResourceDTO);
        List<MenuResourceDTO> children = toTree(menuResource.getChildren());
        children.sort(Comparator.comparing(m -> m.getOrder() == null ? Integer.MAX_VALUE : m.getOrder()));
        menuResourceDTO.setChildren(children);
        return menuResourceDTO;
    }

    private MenuResourceDTO to(MenuResource menuResource, Map<Long, List<HttpResource>> httpResourceMap, Map<Long, List<GraphqlResourceEntity>> graphqlResourceMap) {
        if ( menuResource == null ) {
            return null;
        }

        MenuResourceDTO menuResourceDTO = new MenuResourceDTO();
        BeanUtils.copyProperties(menuResource, menuResourceDTO);
        Long id = menuResource.getId();
        List<MenuResourceDTO> children = toTree(menuResource.getChildren(), httpResourceMap, graphqlResourceMap);

        children.addAll(httpResourceMap.getOrDefault(id, List.of()).stream().map(this::toMenuResource).toList());
        children.addAll(graphqlResourceMap.getOrDefault(id, List.of()).stream().map(this::toMenuResource).toList());
        children.sort(Comparator.comparing(m -> m.getOrder() == null ? Integer.MAX_VALUE : m.getOrder()));
        menuResourceDTO.setChildren(children);
        return menuResourceDTO;
    }


    private List<MenuResource> getRootMenu(List<MenuResource> menuResources) {
        List<MenuResource> result = new ArrayList<>();
        for (MenuResource menuResource : menuResources) {
            MenuResource parentMenu = menuResource.getParentMenu();
            if (parentMenu != null) continue;
            if (!Hibernate.isInitialized(parentMenu)) {
                if (parentMenu.getId() == null) {
                    continue;
                }
            }
            result.add(menuResource);
        }
        return result;
    }

    private List<MenuResourceDTO> toTree(List<MenuResourceDTO> rootMenuResources, List<MenuResourceDTO> mnuResources, Map<Long, List<HttpResource>> httpResourceMap, Map<Long, List<GraphqlResourceEntity>> graphqlResourceMap) {
       return null;
    }

    private List<MenuResourceDTO> toTree(List<MenuResource> menuResources, Map<Long, List<HttpResource>> httpResourceMap, Map<Long, List<GraphqlResourceEntity>> graphqlResourceMap) {
        List<MenuResourceDTO> result = new ArrayList<>();
        for (MenuResource menuResource : menuResources) {
            result.add(to(menuResource, httpResourceMap, graphqlResourceMap));
        }
        return result;
    }

    private MenuResourceDTO toMenuResource(HttpResource httpResource) {
        MenuResourceDTO menuResource = new MenuResourceDTO();
        menuResource.setType("BUTTON");
        BeanUtils.copyProperties(httpResource, menuResource);
        return menuResource;
    }

    private MenuResourceDTO toMenuResource(GraphqlResourceEntity graphqlResource) {
        MenuResourceDTO menuResource = new MenuResourceDTO();
        menuResource.setType("BUTTON");
        BeanUtils.copyProperties(graphqlResource, menuResource);
        return menuResource;
    }



    @Override
    public Iterable<MenuResource> button(Long id) {
        QMenuResource menuResource = QMenuResource.menuResource;
        return menuResourceDao.findAll(menuResource.parentMenu.id.eq(id));
//        return httpResourceDao.findAllByMenuId(id);
//        return QHttpResource.httpResource.menuId.eq(id)
    }

    @Override
    public Boolean validateMenuPath(String path) {
        return menuResourceDao.existsByPath(path);
    }


}
