package com.zjj.security.abac.component.annotation;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.core.annotation.AliasFor;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;

import java.lang.annotation.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月18日 21:49
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@EnableReactiveMethodSecurity
public @interface EnableReactiveAbac {

    @AliasFor(annotation = EnableReactiveMethodSecurity.class)
    boolean prePostEnabled() default false;

    @AliasFor(annotation = EnableReactiveMethodSecurity.class)
    boolean securedEnabled() default false;

    /**
     * Determines if JSR-250 annotations should be enabled. Default is false.
     * @return true if JSR-250 should be enabled false otherwise
     */
    @AliasFor(annotation = EnableReactiveMethodSecurity.class)
    boolean jsr250Enabled() default false;

    /**
     * Indicate whether subclass-based (CGLIB) proxies are to be created as opposed to
     * standard Java interface-based proxies. The default is {@code false}. <strong>
     * Applicable only if {@link #mode()} is set to {@link AdviceMode#PROXY}</strong>.
     * <p>
     * Note that setting this attribute to {@code true} will affect <em>all</em>
     * Spring-managed beans requiring proxying, not just those marked with
     * {@code @Cacheable}. For example, other beans marked with Spring's
     * {@code @Transactional} annotation will be upgraded to subclass proxying at the same
     * time. This approach has no negative impact in practice unless one is explicitly
     * expecting one type of proxy vs another, e.g. in tests.
     * @return true if subclass-based (CGLIB) proxies are to be created
     */
    @AliasFor(annotation = EnableReactiveMethodSecurity.class)
    boolean proxyTargetClass() default false;

    /**
     * Indicate how security advice should be applied. The default is
     * {@link AdviceMode#PROXY}.
     * @see AdviceMode
     * @return the {@link AdviceMode} to use
     */
    @AliasFor(annotation = EnableReactiveMethodSecurity.class)
    AdviceMode mode() default AdviceMode.PROXY;

    /**
     * Indicate additional offset in the ordering of the execution of the security
     * interceptors when multiple advices are applied at a specific joinpoint. I.e.,
     * precedence of each security interceptor enabled by this annotation will be
     * calculated as sum of its default precedence and offset. The default is 0.
     * @return the offset in the order the security advisor should be applied
     * @since 6.3
     */
    @AliasFor(annotation = EnableReactiveMethodSecurity.class)
    int offset() default 0;
}
