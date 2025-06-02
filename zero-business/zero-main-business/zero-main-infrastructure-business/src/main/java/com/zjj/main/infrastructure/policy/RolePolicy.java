package com.zjj.main.infrastructure.policy;

import com.zjj.autoconfigure.component.redis.RedisStringRepository;
import com.zjj.autoconfigure.component.security.rbac.GraphqlResource;
import com.zjj.autoconfigure.component.security.rbac.RbacCacheManage;
import com.zjj.autoconfigure.component.tenant.MultiTenancyProperties;
import com.zjj.main.domain.role.event.InitRbacEvent;
import com.zjj.main.infrastructure.db.jpa.dao.GraphqlResourceDao;
import com.zjj.main.infrastructure.db.jpa.dao.PermissionDao;
import com.zjj.main.infrastructure.db.jpa.dao.RoleDao;
import com.zjj.main.infrastructure.db.jpa.entity.*;
import com.zjj.security.rbac.client.component.GraphqlTemplateSupper;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
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
    private PermissionDao permissionDao;
    @Autowired
    private GraphqlResourceDao graphqlResourceDao;
//    @Autowired
//    private GraphqlTemplateSupper graphqlTemplateSupper;

    @Async
    @EventListener
    @Transactional(readOnly = true)
    public void on(InitRbacEvent event) {

        // *************************************************************************
        //    暂时使用
        // *************************************************************************

        // *************************************************************************


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
        Map<String, String> permissionMap = permissionDao
                .findAll()
                .filter(p -> !p.isEmpty())
//                .filter(p -> {
//        //            Resource resource = p.getResource();
//                    if (p instanceof HttpResource) {
//                        return true;
//                    }
//                    return true;
//                })
                .collect(Collectors.toMap(p -> p.getKey(), Permission::getCode));
        rbacCacheManage.batchPutPermission(permissionMap);
        log.info("初始化rbac完成");
    }
}
