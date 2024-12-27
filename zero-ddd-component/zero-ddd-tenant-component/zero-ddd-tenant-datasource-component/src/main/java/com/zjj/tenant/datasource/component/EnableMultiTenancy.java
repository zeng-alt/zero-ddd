package com.zjj.tenant.datasource.component;


import com.zjj.autoconfigure.component.tenant.MultiTenancyProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月23日 21:52
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@EnableConfigurationProperties({MultiTenancyProperties.class, LiquibaseProperties.class})
@Import(TenantSelector.class)
public @interface EnableMultiTenancy {

    /**
     * 使用多租户模式
     * @return 模式
     */
    TenantMode mode() default TenantMode.DATABASE;

    /**
     * 是否启用master的Liquibase
     */
    boolean enabledLiquibase() default true;

    /**
     * 是否启动动态添加数据源时候执行Liquibase
     */
    boolean dynamicLiquibase() default true;

}
