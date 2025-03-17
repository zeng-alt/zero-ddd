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
    String[] value() default "";

    @AliasFor("value")
    String[] name() default "";

    /**
     * 多个文件导出时，文件自动压缩成zip文件<br/>
     * 单个文件时未设置zipName不生成压缩文件，设置则生成压缩文件
     */
    String zipName() default "";

    boolean dynamic() default false;
}
