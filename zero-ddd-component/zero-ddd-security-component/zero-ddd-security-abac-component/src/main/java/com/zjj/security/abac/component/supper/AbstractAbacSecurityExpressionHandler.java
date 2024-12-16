package com.zjj.security.abac.component.supper;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.MethodResolver;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.DenyAllPermissionEvaluator;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.expression.SecurityExpressionOperations;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.Authentication;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月16日 21:13
 */
public abstract class AbstractAbacSecurityExpressionHandler<T>
        implements SecurityExpressionHandler<T>, ApplicationContextAware {

    private ExpressionParser expressionParser = new SpelExpressionParser();
    private PermissionEvaluator permissionEvaluator = new DenyAllPermissionEvaluator();
    private RoleHierarchy roleHierarchy;
    private MethodResolver rootMethodResolver;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }

    /**
     * @return an expression parser for the expressions used by the implementation.
     */
    @Override
    public ExpressionParser getExpressionParser() {
        return expressionParser;
    }

    /**
     * Provides an evaluation context in which to evaluate security expressions for the
     * invocation type.
     *
     * @param authentication
     * @param invocation
     */
    @Override
    public EvaluationContext createEvaluationContext(Authentication authentication, T invocation) {
        SecurityExpressionOperations root = createSecurityExpressionRoot(authentication, invocation);
        return createEvaluationContextInternal(authentication, invocation, root);
    }

    protected RoleHierarchy getRoleHierarchy() {
        return this.roleHierarchy;
    }

    public void setRoleHierarchy(RoleHierarchy roleHierarchy) {
        this.roleHierarchy = roleHierarchy;
    }


    protected PermissionEvaluator getPermissionEvaluator() {
        return this.permissionEvaluator;
    }

    protected abstract SecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication,
                                                                                 T invocation);

    // TODO
    protected SimpleEvaluationContext createEvaluationContextInternal(Authentication authentication, T invocation, SecurityExpressionOperations root) {
//        return SimpleEvaluationContext.forReadOnlyDataBinding().withRootObject(root).withMethodResolvers(rootMethodResolver).build();
        return SimpleEvaluationContext.forReadOnlyDataBinding().withRootObject(root).build();
    }
}
