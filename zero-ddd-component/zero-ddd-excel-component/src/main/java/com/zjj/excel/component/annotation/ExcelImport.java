package com.zjj.excel.component.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ValueConstants;

import java.lang.annotation.*;


/** 使用@{like List}
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月17日 11:20
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelImport {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    /**
     * Whether the parameter is required.
     * <p>Defaults to {@code true}, leading to an exception being thrown
     * if the parameter is missing in the request. Switch this to
     * {@code false} if you prefer a {@code null} value if the parameter is
     * not present in the request.
     * <p>Alternatively, provide a {@link #defaultValue}, which implicitly
     * sets this flag to {@code false}.
     */
    boolean required() default true;

    boolean dynamic() default false;

    /**
     * The default value to use as a fallback when the request parameter is
     * not provided or has an empty value.
     * <p>Supplying a default value implicitly sets {@link #required} to
     * {@code false}.
     */
    String defaultValue() default ValueConstants.DEFAULT_NONE;
}
