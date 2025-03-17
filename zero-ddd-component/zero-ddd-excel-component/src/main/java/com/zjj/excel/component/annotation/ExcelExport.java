package com.zjj.excel.component.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月17日 15:41
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelExport {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";
}
