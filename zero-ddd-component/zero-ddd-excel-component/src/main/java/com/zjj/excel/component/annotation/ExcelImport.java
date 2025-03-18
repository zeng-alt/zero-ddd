package com.zjj.excel.component.annotation;

import io.reactivex.rxjava3.core.Flowable;
import org.springframework.core.annotation.AliasFor;

import java.util.List;
import java.lang.annotation.*;


/**
 * 使用{@link List} 只推荐小文件 <br>
 * 使用{@link Flowable} 推荐方式，响应式
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


    /**
     * 是否动态解析，动态解析时，会根据Excel的行数动态解析，<br>
     * 如果Excel的行数小于等于1000，则使用{@link List}，<br>
     * 如果Excel的行数大于1000，则使用{@link Flowable} <br>
     */
    boolean dynamic() default false;


    /**
     * 多个文件时是否合并成List或{@link Flowable}<br/>
     * 如果为false并且有多个文件，接收参数为{@code List<Object>或者Flowable<Object>},只取第一个文件 <br/>
     * 接收参数为{@code List<List<Object>>或者Flowable<Flowable<Object>>}, 此参数失效
     */
    boolean merge() default false;
}
