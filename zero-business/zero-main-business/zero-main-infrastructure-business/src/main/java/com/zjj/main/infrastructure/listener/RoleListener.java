package com.zjj.main.infrastructure.listener;

import com.zjj.autoconfigure.component.redis.RedisStringRepository;
import com.zjj.autoconfigure.component.security.rbac.RbacCacheManage;
import com.zjj.bean.componenet.BeanHelper;
import com.zjj.main.domain.role.event.*;
import com.zjj.main.infrastructure.db.jpa.dao.GraphqlResourceDao;
import com.zjj.main.infrastructure.db.jpa.dao.PermissionDao;
import com.zjj.main.infrastructure.db.jpa.dao.RoleDao;
import com.zjj.main.infrastructure.db.jpa.dao.RolePermissionDao;
import com.zjj.main.infrastructure.db.jpa.entity.Permission;
import com.zjj.main.infrastructure.db.jpa.entity.Role;
import com.zjj.main.infrastructure.db.jpa.entity.RolePermission;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    private final RedisStringRepository redisStringRepository;
    private final RbacCacheManage rbacCacheManage;

    @ApplicationModuleListener
    public void on(StockInRoleEvent event) {
        log.info("角色入库事件：{}", event);
        try {
            this.redisStringRepository.tryLock(event.get_tenant() + ":role:" + event.getCode(), 5, TimeUnit.SECONDS);
            Role role = roleDao.findByCode(event.getCode())
                    .getOrElseThrow(() -> new RuntimeException("角色不存在"));
            BeanUtils.copyProperties(event, role);
            this.roleDao.save(role);
            this.rbacCacheManage.putRole(event.getCode(), new HashSet<>());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            redisStringRepository.unlock(event.get_tenant() + ":role:" + event.getCode());
        }
    }

    @ApplicationModuleListener
    public void on(DeleteRoleEvent event) {
        log.info("删除角色事件：{}", event);
        try {
            this.redisStringRepository.tryLock(event.get_tenant() + ":role:" + event.getRoleCode(), 5, TimeUnit.SECONDS);
            this.roleDao.deleteByCode(event.getRoleCode());
            this.rbacCacheManage.removeRole(event.getRoleCode());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            redisStringRepository.unlock(event.get_tenant() + ":role:" + event.getRoleCode());
        }
    }

    @ApplicationModuleListener
    public void on(StockInRolePermissionEvent event) {
        handleRolePermissionEvent(
                event.get_tenant(),
                event.getRoleCode(),
                event.getPermissionId(),
                true
        );
    }

    @ApplicationModuleListener
    public void on(DeleteRolePermissionEvent event) {
        handleRolePermissionEvent(
                event.get_tenant(),
                event.getRoleCode(),
                event.getPermissionId(),
                false
        );
    }

    @ApplicationModuleListener
    public void on(FunctionAuthorizeEvent event) {
        handleRolePermissionByIdEvent(
                event.get_tenant(),
                event.getRoleId(),
                event.getGraphqlIds(),
                true
        );
    }

    @ApplicationModuleListener
    public void on(FunctionCancelAuthorizeEvent event) {
        handleRolePermissionByIdEvent(
                event.get_tenant(),
                event.getRoleId(),
                event.getGraphqlIds(),
                false
        );
    }

    @ApplicationModuleListener
    public void on(ServiceAuthorizeEvent event) {
        handleRolePermissionByUri(
                event.get_tenant(),
                event.getRoleId(),
                event.getService(),
                true
        );
    }

    @ApplicationModuleListener
    public void on(ServiceCancelAuthorizeEvent event) {
        handleRolePermissionByUri(
                event.get_tenant(),
                event.getRoleId(),
                event.getService(),
                false
        );
    }

    // ===================== 通用私有方法 ===========================

    private void handleRolePermissionEvent(String tenant, String roleCode, Collection<Long> permissionIds, boolean isSave) {
        Role role = roleDao.findByCode(roleCode)
                .getOrElseThrow(() -> new RuntimeException("角色不存在"));
        List<Permission> permissions = permissionDao.findAllByIdIn(permissionIds).toList();
        handleWithLock(tenant, role, permissions, isSave);
    }

    private void handleRolePermissionByIdEvent(String tenant, Long roleId, Collection<Long> permissionIds, boolean isSave) {
        Role role = roleDao.findById(roleId)
                .getOrElseThrow(() -> new RuntimeException("角色不存在"));
        List<Permission> permissions = permissionDao.findAllByIdIn(permissionIds).toList();
        handleWithLock(tenant, role, permissions, isSave);
    }

    private void handleRolePermissionByUri(String tenant, Long roleId, String service, boolean isSave) {
        Role role = roleDao.findById(roleId)
                .getOrElseThrow(() -> new RuntimeException("角色不存在"));
        List<Permission> permissions = graphqlResourceDao.findAllByUri(service).map(g -> (Permission) g).toList();
        handleWithLock(tenant, role, permissions, isSave);
    }

    private void handleWithLock(String tenant, Role role, List<Permission> permissionsToModify, boolean isSave) {
        String lockKey = tenant + ":role:" + role.getCode();
        try {
            redisStringRepository.tryLock(lockKey, 5, TimeUnit.SECONDS);

            if (isSave) {
                List<RolePermission> newRelations = buildRolePermissions(role, permissionsToModify);
                rolePermissionDao.saveAll(newRelations);
            } else {
                rolePermissionDao.deleteByRoleAndPermissions(role, permissionsToModify);
            }

            Set<String> permissionCodes = mergeCurrentPermissions(role, permissionsToModify, isSave);
            rbacCacheManage.putRole(role.getCode(), permissionCodes);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            redisStringRepository.unlock(lockKey);
        }
    }

    private List<RolePermission> buildRolePermissions(Role role, List<Permission> permissions) {
        return permissions.stream().map(p -> {
            RolePermission rp = new RolePermission();
            rp.setRole(role);
            rp.setPermission(p);
            return rp;
        }).toList();
    }

    private Set<String> mergeCurrentPermissions(Role role, List<Permission> changedPermissions, boolean isAdd) {
        Set<String> all = role.getRolePermissions().stream()
                .map(r -> r.getPermission().getCode())
                .collect(Collectors.toSet());

        Set<String> changeSet = changedPermissions.stream()
                .map(Permission::getCode)
                .collect(Collectors.toSet());

        if (isAdd) {
            all.addAll(changeSet);
        } else {
            all.removeAll(changeSet);
        }

        return all;
    }
}

