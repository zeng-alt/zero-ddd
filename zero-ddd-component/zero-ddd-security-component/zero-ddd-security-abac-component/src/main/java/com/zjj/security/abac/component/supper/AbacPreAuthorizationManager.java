package com.zjj.security.abac.component.supper;

import com.zjj.autoconfigure.component.security.abac.PolicyRule;
import com.zjj.security.abac.component.spi.EnvironmentAttribute;
import com.zjj.security.abac.component.spi.ObjectAttribute;
import com.zjj.security.abac.component.spi.SubjectAttribute;
import lombok.RequiredArgsConstructor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.expression.EvaluationContext;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.authorization.ExpressionAuthorizationDecision;
import org.springframework.security.authorization.method.MethodAuthorizationDeniedHandler;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class AbacPreAuthorizationManager implements AuthorizationManager<MethodInvocation>, MethodAuthorizationDeniedHandler {

    private final AbacPreAuthorizeExpressionAttributeRegistry registry;

    public AuthorizationDecision check(Supplier<Authentication> authentication, MethodInvocation mi) {
        PolicyRule policyRule = this.registry.getPolicyRule(authentication, mi);
        if (policyRule == null) {
            return null;
        }

        EvaluationContext context = this.registry.getExpressionHandler().createEvaluationContext(authentication, mi);
        return new ExpressionAuthorizationDecision(policyRule.getTarget().getValue(context, Boolean.class), policyRule.getTarget());
    }


    @Override
    public Object handleDeniedInvocation(MethodInvocation methodInvocation, AuthorizationResult authorizationResult) {
        return null;
    }
}