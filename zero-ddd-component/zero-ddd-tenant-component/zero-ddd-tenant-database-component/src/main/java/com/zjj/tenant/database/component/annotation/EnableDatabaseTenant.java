package com.zjj.tenant.database.component.annotation;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月19日 21:30
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface EnableDatabaseTenant {
}
