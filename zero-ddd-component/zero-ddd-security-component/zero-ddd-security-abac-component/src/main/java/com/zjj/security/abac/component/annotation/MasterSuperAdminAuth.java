package com.zjj.security.abac.component.annotation;

import com.zjj.security.abac.component.configuration.AdminPolicyType;

import java.lang.annotation.*;

/**
 * 在主租户并且用户名为superAdmin的用户可以访问
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月31日 21:05
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@AbacAdminAuth(policy = AdminPolicyType.MASTER_SUPER_ADMIN)
public @interface MasterSuperAdminAuth {
}
