package com.zjj.main.infrastructure.policy;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zjj.autoconfigure.component.redis.RedisStringRepository;
import com.zjj.autoconfigure.component.security.rbac.RbacCacheManage;
import com.zjj.autoconfigure.component.tenant.MultiTenancyProperties;
import com.zjj.autoconfigure.component.tenant.TenantContextHolder;
import com.zjj.main.domain.role.event.InitRbacEvent;
import com.zjj.main.infrastructure.db.jpa.dao.*;
import com.zjj.main.infrastructure.db.jpa.entity.MenuResource;
import com.zjj.main.infrastructure.db.jpa.entity.Permission;
import com.zjj.main.infrastructure.db.jpa.entity.Resource;
import com.zjj.main.infrastructure.db.jpa.entity.RolePermission;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月07日 10:11
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RolePolicy {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RbacCacheManage rbacCacheManage;

    @Autowired
    private MultiTenancyProperties multiTenancyProperties;
    @Autowired
    private RedisStringRepository redisStringRepository;
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private ResourceDao resourceDao;
    @Autowired
    private GraphqlResourceDao graphqlResourceDao;


    @Async
    @EventListener
    @Transactional(readOnly = true)
    public void on(InitRbacEvent event) {
        Map<String, Set<String>> roleMap = roleDao.findAll().map(r -> {
            String code = r.getCode();
            Set<String> permissions = r.getRolePermissions()
                    .stream()
                    .map(RolePermission::getPermission)
                    .map(Permission::getCode)
                    .collect(Collectors.toSet());
            return Tuple.of(code, permissions);
        }).collect(Collectors.toMap(t -> t._1, Tuple2::_2));
        rbacCacheManage.putRole(roleMap);
        Map<String, String> permissionMap = permissionDao.findAll().filter(p -> {
            Resource resource = p.getResource();
            if (resource instanceof MenuResource menuResource) {
                return "BUTTON".equals(menuResource.getType());
            }
            return true;
        }).collect(Collectors.toMap(p -> p.getResource().getKey(), Permission::getCode));
        rbacCacheManage.batchPutPermission(permissionMap);
        log.info("初始化rbac完成");
    }
}
