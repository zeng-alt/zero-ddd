package com.zjj.security.abac.component.annotation;

import com.zjj.security.abac.component.configuration.AdminPolicyType;

import java.lang.annotation.*;

/**
 * 要为主租户角色为admin的用户才允许访问
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月31日 21:05
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@AbacAdminAuth(policy = AdminPolicyType.MASTER_ADMIN)
public @interface MasterAdminAuth {
}
