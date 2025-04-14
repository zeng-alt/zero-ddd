package com.zjj.main.infrastructure.policy;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zjj.autoconfigure.component.redis.RedisStringRepository;
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
    private ResourceDao resourceDao;
    @Autowired
    private MenuResourceDao menuResourceDao;
    @Autowired
    private GraphqlResourceDao graphqlResourceDao;

    @Autowired
    private MultiTenancyProperties multiTenancyProperties;
    @Autowired
    private RedisStringRepository redisStringRepository;
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Async
    @EventListener
    @Transactional(readOnly = true)
    public void on(InitRbacEvent event) {


        String tenantId = (event.get_tenant() == null ? multiTenancyProperties.getMaster() : TenantContextHolder.getTenantId()) + ":";
        String db = event.get_database() == null ? "" : ":" + TenantContextHolder.getDatabase() + ":";
        String schema = event.get_schema() == null ? "" : ":" + TenantContextHolder.getSchema() + ":";
        String prefix = "rbac:" + tenantId + db + schema;

        Map<String, Set<String>> roleMap = roleDao.findAll().map(r -> {
            String code = r.getCode();
            Set<String> permissions = r.getRolePermissions()
                    .stream()
                    .map(RolePermission::getPermission)
                    .map(Permission::getCode)
                    .collect(Collectors.toSet());
            return Tuple.of(code, permissions);
        }).collect(Collectors.toMap(t -> t._1, Tuple2::_2));
        redisStringRepository.batchPut(prefix + "role:", roleMap);

        Map<String, String> permissionMap = permissionDao.findAll().filter(p -> {
            Resource resource = p.getResource();
            if (resource instanceof MenuResource menuResource) {
                return "BUTTON".equals(menuResource.getType());
            }
            return true;
        }).collect(Collectors.toMap(p -> p.getResource().getKey(), Permission::getCode));
        redisStringRepository.batchPut(prefix, permissionMap);

        log.info("初始化rbac完成");
    }

    //        MenuResource menuResource = new MenuResource();
//        menuResource.setMethod("Get");
//        menuResource.setPath("/main/v1/user/detail");
//        MenuResource menuResource1 = new MenuResource();
//        menuResource1.setMethod("Post");
//        menuResource1.setPath("/main/graphql");
//        resourceDao.save(menuResource);
//        resourceDao.save(menuResource1);
//
//        Permission permission = new Permission();
//        permission.setCode("get:user:detail");
//        permission.setResource(menuResource);
//
//        Permission permission1 = new Permission();
//        permission1.setCode("post:main:graphql");
//        permission1.setResource(menuResource1);
//
//        permissionDao.save(permission);
//        permissionDao.save(permission1);
//
//        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
//                .withIgnoreNullValues()
//                .withIgnoreCase()
//                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
//        Resource resource = new Resource();
//        resource.setResourceType("HTTP");
//        resourceDao.findAll(Example.of(resource, exampleMatcher));
}
