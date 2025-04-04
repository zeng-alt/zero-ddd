package com.zjj.security.abac.component.annotation;

import com.zjj.security.abac.component.configuration.AdminPolicyType;

import java.lang.annotation.*;

/**
 * 角色为admin的用户才能访问
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月31日 21:04
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@AbacAdminAuth(policy = AdminPolicyType.ADMIN)
public @interface AdminAuth {
}
