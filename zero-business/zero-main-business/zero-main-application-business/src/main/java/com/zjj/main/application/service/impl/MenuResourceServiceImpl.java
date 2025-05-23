package com.zjj.main.application.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zjj.main.application.service.MenuResourceService;
import com.zjj.main.infrastructure.db.jpa.dao.MenuResourceDao;
import com.zjj.main.infrastructure.db.jpa.dao.PermissionDao;
import com.zjj.main.infrastructure.db.jpa.dao.RoleDao;
import com.zjj.main.infrastructure.db.jpa.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月09日 16:43
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MenuResourceServiceImpl implements MenuResourceService {

    private final MenuResourceDao menuResourceDao;
    private final PermissionDao permissionDao;
    private final RoleDao roleDao;
    private final JPAQueryFactory jpaQueryFactory;

    public Iterable<MenuResource> tree(String username, String roleCode) {

        if ("superAdmin".equals(username)) {
            return this.tree();
        }
        QRolePermission rolePermission = QRolePermission.rolePermission;
        QPermission permission = QPermission.permission;
        QMenuResource menuResource = QMenuResource.menuResource;


        return jpaQueryFactory
                // 同时在 from 中包含 rolePermission 与 menuResource
                .select(menuResource)
                .from(rolePermission, menuResource)
                // 关联 RolePermission 与 Permission
                .join(rolePermission.permission, permission)
                .where(
                        // 关联 Permission.resource 与 MenuResource，依赖于 JOINED 策略下共享的 ID
                        // 限定资源类型为 "HTTP"
                        // 角色编码条件
                        rolePermission.role.code.eq(roleCode),
                        // 只取顶级菜单（没有父菜单的）
                        menuResource.parentMenu.isNull(),
                        menuResource.type.eq("MENU")
                )
                .orderBy(menuResource.order.asc())
                .fetch();
    }

    @Override
    public Iterable<MenuResource> tree() {
        QMenuResource menuResource = QMenuResource.menuResource;
        return menuResourceDao.findAll(menuResource.parentMenu.isNull().and(menuResource.type.eq("MENU")), menuResource.order.asc());
    }

    @Override
    public Iterable<MenuResource> treeAll() {
        QMenuResource menuResource = QMenuResource.menuResource;
        return menuResourceDao.findAll(menuResource.parentMenu.isNull(), menuResource.order.asc());
    }

    @Override
    public Iterable<MenuResource> button(Long id) {
        QMenuResource menuResource = QMenuResource.menuResource;
        return menuResourceDao.findAll(menuResource.parentMenu.id.eq(id).and(menuResource.type.eq("BUTTON")), menuResource.order.asc());
    }


}
