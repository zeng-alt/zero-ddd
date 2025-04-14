package com.zjj.security.abac.component.supper;

import com.zjj.autoconfigure.component.security.abac.PolicyRule;
import com.zjj.autoconfigure.component.tenant.TenantDetail;
import com.zjj.security.abac.component.ExpressionUtils;
import com.zjj.security.abac.component.annotation.AbacAdminAuth;
import lombok.RequiredArgsConstructor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.lang.Nullable;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;

import java.lang.reflect.Method;
import java.util.function.Supplier;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年01月02日 14:39
 */
@RequiredArgsConstructor
public class AbacAdminAuthManager implements AuthorizationManager<MethodInvocation> {

    private final AbacPreAuthorizeExpressionAttributeRegistry registry;
    private final SpelExpressionParser spelExpressionParser = new SpelExpressionParser();

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, MethodInvocation mi) {

        PolicyRule policyRule = null;

        AbacAdminAuth abacAdminAuth = findAbacPreAuthorizeAnnotation(mi.getMethod());
        if (abacAdminAuth != null) {
            policyRule = switch (abacAdminAuth.policy()) {
                case ADMIN -> {
                    if (authentication.get() != null && "superAdmin".equals(authentication.get().getName())) {
                        yield PolicyRule.TRUE_RULE;
                    } else {
                        yield PolicyRule.ADMIN_AUTH_RULE;
                    }
                }
                case SUPER_ADMIN -> PolicyRule.SUPER_ADMIN_AUTH_RULE;
                case MASTER_ADMIN -> {
                    if (authentication.get() == null || !"superAdmin".equals(authentication.get().getName())) {
                        yield PolicyRule.MASTER_ADMIN_AUTH_RULE;
                    }

                    Object principal = authentication.get().getPrincipal();
                    if (principal instanceof TenantDetail tenantDetail && "master".equals(tenantDetail.getTenantName())) {
                        yield PolicyRule.TRUE_RULE;
                    }

                    yield PolicyRule.MASTER_ADMIN_AUTH_RULE;
                }
                case MASTER_SUPER_ADMIN -> PolicyRule.MASTER_SUPER_ADMIN_AUTH_RULE;
            };
        }

        if (policyRule == null) {
            return null;
        }
        EvaluationContext context = this.registry.getExpressionHandler().createEvaluationContext(authentication, mi);
        return (AuthorizationDecision) ExpressionUtils.evaluate(spelExpressionParser.parseExpression(policyRule.getCondition()), context);
    }

    @Nullable
    private AbacAdminAuth findAbacPreAuthorizeAnnotation(Method method) {
        return AnnotatedElementUtils.findMergedAnnotation(method, AbacAdminAuth.class);
    }
}
