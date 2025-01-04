package com.zjj.security.abac.component.annotation;

import com.zjj.security.abac.component.configuration.AdminPolicyType;

import java.lang.annotation.*;

/**
 * 根据policy来使用不同的权限校验
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年01月01日 21:59
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface AbacAdminAuth {

    AdminPolicyType policy();
}
