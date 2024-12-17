package com.zjj.security.abac.component.supper;

import com.zjj.autoconfigure.component.security.abac.PolicyRule;
import com.zjj.security.abac.component.ExpressionUtils;
import io.vavr.Tuple2;
import lombok.RequiredArgsConstructor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.expression.EvaluationContext;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Flux;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@RequiredArgsConstructor  //TODO MethodAuthorizationDeniedHandler
public class AbacPreAuthorizationManager implements AuthorizationManager<MethodInvocation> {

    private final AbacPreAuthorizeExpressionAttributeRegistry registry;

    public AuthorizationDecision check(Supplier<Authentication> authentication, MethodInvocation mi) {
        PolicyRule policyRule = this.registry.getPolicyRule(authentication, mi);
        if (policyRule == null) {
            return null;
        }

        Flux<Tuple2<String, Object>> flux = this.registry.getObjectAttribute(mi);
        EvaluationContext context = this.registry.getExpressionHandler().createEvaluationContext(authentication, mi);
        Map<String, Object> map = new ConcurrentHashMap<>();

        flux.reduce(map, (m, t) -> {
            m.put(t._1, t._2);
            return m;
        }).subscribe(m -> context.setVariable("object", m));

        return (AuthorizationDecision) ExpressionUtils.evaluate(policyRule.getCondition(), context);
    }


//    @Override
//    public Object handleDeniedInvocation(MethodInvocation methodInvocation, AuthorizationResult authorizationResult) {
//        return this.registry.getHandler().handleDeniedInvocation(methodInvocation, authorizationResult);
//    }
}