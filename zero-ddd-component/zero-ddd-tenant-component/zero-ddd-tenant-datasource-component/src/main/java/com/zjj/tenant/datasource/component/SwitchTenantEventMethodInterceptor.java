package com.zjj.tenant.datasource.component;

import com.zjj.autoconfigure.component.tenant.TenantContextHolder;
import com.zjj.domain.component.event.TenantEvent;
import lombok.Setter;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.framework.AopInfrastructureBean;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.Pointcuts;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.NonNull;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月24日 17:22
 */
public class SwitchTenantEventMethodInterceptor implements Ordered, MethodInterceptor, PointcutAdvisor, AopInfrastructureBean {

    @Setter
    private int order = HIGHEST_PRECEDENCE;
    private final Pointcut pointcut;

    public SwitchTenantEventMethodInterceptor() {
        this(new ComposablePointcut(Pointcuts.union(new AnnotationMatchingPointcut(null, ApplicationModuleListener.class, true),
                new AnnotationMatchingPointcut(ApplicationModuleListener.class, true))));
    }

    public SwitchTenantEventMethodInterceptor(Pointcut pointcut) {
        Assert.notNull(pointcut, "pointcut cannot be null");
        this.pointcut = pointcut;
    }

    @Nullable
    @Override
    public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {

        ApplicationModuleListener switchAnnotation = findSwitchAnnotation(invocation.getMethod());
        if (switchAnnotation != null) {
            Object[] arguments = invocation.getArguments();
            if (!ArrayUtils.isEmpty(arguments)) {
                Object argument = arguments[0];
                if (argument instanceof TenantEvent tenantEvent) {
                    TenantContextHolder.setTenantId(tenantEvent.getTenant$());
                    TenantContextHolder.setSchema(tenantEvent.getSchema$());
                    TenantContextHolder.setDatabase(tenantEvent.getDatabase$());
                }
            }
        }

        return invocation.proceed();

    }

    /**
     * Get the Pointcut that drives this advisor.
     */
    @Override
    public @NonNull Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public @NonNull Advice getAdvice() {
        return this;
    }

    @Override
    public int getOrder() {
        return order;
    }


    private ApplicationModuleListener findSwitchAnnotation(Method method) {
        return AnnotatedElementUtils.findMergedAnnotation(method, ApplicationModuleListener.class);
    }
}
