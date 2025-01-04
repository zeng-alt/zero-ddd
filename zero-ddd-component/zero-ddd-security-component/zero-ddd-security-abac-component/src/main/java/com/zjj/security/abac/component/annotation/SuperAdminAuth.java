package com.zjj.security.abac.component.annotation;

import com.zjj.security.abac.component.configuration.AdminPolicyType;

import java.lang.annotation.*;

/**
 * 用户名为superAdmin的用户可以访问
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月31日 21:04
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@AbacAdminAuth(policy = AdminPolicyType.SUPER_ADMIN)
public @interface SuperAdminAuth {
}
