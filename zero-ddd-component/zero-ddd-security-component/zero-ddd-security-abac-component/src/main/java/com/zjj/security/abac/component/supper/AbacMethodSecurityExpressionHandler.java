package com.zjj.security.abac.component.supper;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.security.access.expression.AbstractSecurityExpressionHandler;
import org.springframework.security.access.expression.SecurityExpressionOperations;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.core.Authentication;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月12日 21:34
 */
public class AbacMethodSecurityExpressionHandler extends AbstractSecurityExpressionHandler<MethodInvocation>
        implements MethodSecurityExpressionHandler {

    /**
     * Implement in order to create a root object of the correct type for the supported
     * invocation type.
     *
     * @param authentication the current authentication object
     * @param invocation     the invocation (filter, method, channel)
     * @return the object
     */
    @Override
    protected SecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication, MethodInvocation invocation) {
        return null;
    }

    /**
     * Filters a target collection or array. Only applies to method invocations.
     *
     * @param filterTarget     the array or collection to be filtered.
     * @param filterExpression the expression which should be used as the filter
     *                         condition. If it returns false on evaluation, the object will be removed from the
     *                         returned collection
     * @param ctx              the current evaluation context (as created through a call to
     *                         {@link #createEvaluationContext(Authentication, Object)}
     * @return the filtered collection or array
     */
    @Override
    public Object filter(Object filterTarget, Expression filterExpression, EvaluationContext ctx) {
        return null;
    }

    /**
     * Used to inform the expression system of the return object for the given evaluation
     * context. Only applies to method invocations.
     *
     * @param returnObject the return object value
     * @param ctx          the context within which the object should be set (as created through a
     *                     call to
     *                     {@link #createEvaluationContext(Authentication, Object)}
     */
    @Override
    public void setReturnObject(Object returnObject, EvaluationContext ctx) {

    }
}
