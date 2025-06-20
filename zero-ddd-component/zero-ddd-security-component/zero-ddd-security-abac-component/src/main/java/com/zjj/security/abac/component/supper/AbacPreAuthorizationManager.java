package com.zjj.security.abac.component.supper;

import com.zjj.autoconfigure.component.security.abac.PolicyRule;
import com.zjj.security.abac.component.ExpressionUtils;
import com.zjj.security.abac.component.ObjectReturn;
import com.zjj.security.abac.component.annotation.AbacPreAuthorize;
import com.zjj.security.abac.component.object.AuthorizeObjectManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;

import java.util.function.Supplier;

@Slf4j
@RequiredArgsConstructor  //TODO MethodAuthorizationDeniedHandler
public class AbacPreAuthorizationManager implements AuthorizationManager<MethodInvocation> {

    private final AbacPreAuthorizeExpressionAttributeRegistry registry;
    private final AuthorizeObjectManager manager;
    private final SpelExpressionParser spelExpressionParser = new SpelExpressionParser();

    public AuthorizationDecision check(Supplier<Authentication> authentication, MethodInvocation mi) {
//        if (authentication.get() != null && "superAdmin".equals(authentication.get().getName())) {
//            return new AuthorizationDecision(true);
//        }

        PolicyRule policyRule = this.registry.getPolicyRule(authentication, mi);
        if (policyRule == null || policyRule.isEmpty()) {
            log.warn("{} :policyRule is null", mi.getMethod().toGenericString());
            return null;
        }

        if (Boolean.FALSE.equals(policyRule.getEnable())) {
            log.warn("{} :policyRule is not enable", mi.getMethod().toGenericString());
            return null;
        }

        EvaluationContext context = this.registry.getExpressionHandler().createEvaluationContext(authentication, mi);
        AbacPreAuthorize annotation = AnnotationUtils.findAnnotation(mi.getMethod(), AbacPreAuthorize.class);
        ObjectReturn[] objectReturns = annotation.objectReturns();
        if (objectReturns != null) {
            for (ObjectReturn objectReturn : objectReturns) {
                String key = objectReturn.value();
                String name = objectReturn.name();
                String[] argument = objectReturn.argument();
                Object o = manager.processObject(key, name, mi, argument);
                context.setVariable(name, o);
            }
        }
        return (AuthorizationDecision) ExpressionUtils.evaluate(spelExpressionParser.parseExpression(policyRule.getCondition()), context);
    }


//    @Override
//    public Object handleDeniedInvocation(MethodInvocation methodInvocation, AuthorizationResult authorizationResult) {
//        return this.registry.getHandler().handleDeniedInvocation(methodInvocation, authorizationResult);
//    }
}