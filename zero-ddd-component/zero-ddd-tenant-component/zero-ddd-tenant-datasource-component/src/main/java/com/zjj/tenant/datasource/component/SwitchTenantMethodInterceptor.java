package com.zjj.tenant.datasource.component;

import com.zjj.autoconfigure.component.tenant.TenantContextHolder;
import lombok.Setter;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.framework.AopInfrastructureBean;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.Pointcuts;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年01月01日 21:15
 */
public class SwitchTenantMethodInterceptor implements Ordered, MethodInterceptor, PointcutAdvisor, AopInfrastructureBean {

    @Setter
    private int order = HIGHEST_PRECEDENCE;
    private final Pointcut pointcut;

    public SwitchTenantMethodInterceptor() {
        this(new ComposablePointcut(Pointcuts.union(new AnnotationMatchingPointcut(null, SwitchTenant.class, true),
                new AnnotationMatchingPointcut(SwitchTenant.class, true))));
    }

    public SwitchTenantMethodInterceptor(Pointcut pointcut) {
        Assert.notNull(pointcut, "pointcut cannot be null");
        this.pointcut = pointcut;
    }

    @Nullable
    @Override
    public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {

        SwitchTenant switchAnnotation = findSwitchAnnotation(invocation.getMethod());
        if (switchAnnotation != null) {
            String tenantId = switchAnnotation.value();
            TenantContextHolder.setTenantId(tenantId);
        }
        try {
            return invocation.proceed();
        } finally {
            TenantContextHolder.clear();
        }
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


    private SwitchTenant findSwitchAnnotation(Method method) {
        return AnnotatedElementUtils.findMergedAnnotation(method, SwitchTenant.class);
    }
}
