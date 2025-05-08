package com.zjj.security.abac.component.supper;

import com.zjj.autoconfigure.component.security.abac.PolicyRule;
import com.zjj.security.abac.component.ExpressionUtils;
import io.vavr.Tuple2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@Slf4j
@RequiredArgsConstructor  //TODO MethodAuthorizationDeniedHandler
public class AbacPreAuthorizationManager implements AuthorizationManager<MethodInvocation> {

    private final AbacPreAuthorizeExpressionAttributeRegistry registry;
    private final SpelExpressionParser spelExpressionParser = new SpelExpressionParser();

    public AuthorizationDecision check(Supplier<Authentication> authentication, MethodInvocation mi) {
        if (authentication.get() != null && "superAdmin".equals(authentication.get().getName())) {
            return new AuthorizationDecision(true);
        }

        PolicyRule policyRule = this.registry.getPolicyRule(authentication, mi);
        if (policyRule == null) {
            log.warn("{} :policyRule is null", mi.getMethod().toGenericString());
            return null;
        }
        EvaluationContext context = this.registry.getExpressionHandler().createEvaluationContext(authentication, mi);
        return (AuthorizationDecision) ExpressionUtils.evaluate(spelExpressionParser.parseExpression(policyRule.getCondition()), context);
    }


//    @Override
//    public Object handleDeniedInvocation(MethodInvocation methodInvocation, AuthorizationResult authorizationResult) {
//        return this.registry.getHandler().handleDeniedInvocation(methodInvocation, authorizationResult);
//    }
}