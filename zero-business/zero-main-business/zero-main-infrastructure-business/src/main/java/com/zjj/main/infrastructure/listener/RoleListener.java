package com.zjj.main.infrastructure.listener;

import com.zjj.autoconfigure.component.security.rbac.GraphqlResource;
import com.zjj.bean.componenet.BeanHelper;
import com.zjj.main.domain.role.event.*;
import com.zjj.main.infrastructure.db.jpa.dao.GraphqlResourceDao;
import com.zjj.main.infrastructure.db.jpa.dao.PermissionDao;
import com.zjj.main.infrastructure.db.jpa.dao.RoleDao;
import com.zjj.main.infrastructure.db.jpa.dao.RolePermissionDao;
import com.zjj.main.infrastructure.db.jpa.entity.GraphqlResourceEntity;
import com.zjj.main.infrastructure.db.jpa.entity.Permission;
import com.zjj.main.infrastructure.db.jpa.entity.Role;
import com.zjj.main.infrastructure.db.jpa.entity.RolePermission;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年05月21日 14:36
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RoleListener {

    private final RoleDao roleDao;
    private final PermissionDao permissionDao;
    private final GraphqlResourceDao graphqlResourceDao;
    private final RolePermissionDao rolePermissionDao;

    @ApplicationModuleListener
    public void on(StockInRoleEvent event) {
        log.info("角色入库事件：{}", event);
        this.roleDao.save(BeanHelper.copyToObject(event, Role.class));
    }

    @ApplicationModuleListener
    public void on(StockInRolePermissionEvent event) {
        log.info("角色权限入库事件：{}", event);
        Role role = this.roleDao.findByCode(event.getRoleCode()).getOrElseThrow(() -> new RuntimeException("角色不存在"));
        List<RolePermission> rolePermissions = this.permissionDao
                .findAllByIdIn(event.getPermissionId())
                .map(p -> {
                    RolePermission rolePermission = new RolePermission();
                    rolePermission.setRole(role);
                    rolePermission.setPermission(p);
                    return rolePermission;
                })
                .toList();
        rolePermissionDao.saveAll(rolePermissions);
    }

    @ApplicationModuleListener
    public void on(DeleteRolePermissionEvent event) {
        log.info("角色权限删除事件：{}", event);
        Role role = this.roleDao.findByCode(event.getRoleCode()).getOrElseThrow(() -> new RuntimeException("角色不存在"));
        List<Permission> rolePermissions = this.permissionDao
                .findAllByIdIn(event.getPermissionId())
                .toList();
        rolePermissionDao.deleteByRoleAndPermissions(role, rolePermissions);
    }

    @ApplicationModuleListener
    public void on(FunctionAuthorizeEvent event) {
        Role role = this.roleDao.findById(event.getRoleId()).getOrElseThrow(() -> new RuntimeException("角色不存在"));
        List<RolePermission> rolePermissions = this.permissionDao
                .findAllByIdIn(event.getGraphqlIds())
                .map(p -> {
                    RolePermission rolePermission = new RolePermission();
                    rolePermission.setRole(role);
                    rolePermission.setPermission(p);
                    return rolePermission;
                })
                .toList();
        rolePermissionDao.saveAll(rolePermissions);
    }

    @ApplicationModuleListener
    public void on(FunctionCancelAuthorizeEvent event) {
        Role role = this.roleDao.findById(event.getRoleId()).getOrElseThrow(() -> new RuntimeException("角色不存在"));
        List<Permission> permissions = this.permissionDao
                .findAllByIdIn(event.getGraphqlIds())
                .toList();

        rolePermissionDao.deleteByRoleAndPermissions(role, permissions);
    }


    @ApplicationModuleListener
    public void on(ServiceAuthorizeEvent event) {
        Role role = this.roleDao.findById(event.getRoleId()).getOrElseThrow(() -> new RuntimeException("角色不存在"));
        List<RolePermission> rolePermissions = this.graphqlResourceDao
                .findAllByUri(event.getService())
                .map(p -> {
                    RolePermission rolePermission = new RolePermission();
                    rolePermission.setRole(role);
                    rolePermission.setPermission(p);
                    return rolePermission;
                })
                .toList();
        rolePermissionDao.saveAll(rolePermissions);
    }

    @ApplicationModuleListener
    public void on(ServiceCancelAuthorizeEvent event) {
        Role role = this.roleDao.findById(event.getRoleId()).getOrElseThrow(() -> new RuntimeException("角色不存在"));
        List<Permission> permissions = this.graphqlResourceDao
                .findAllByUri(event.getService()).map(p -> (Permission) p).toList();
        rolePermissionDao.deleteByRoleAndPermissions(role, permissions);
    }
}
