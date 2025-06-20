package com.zjj.security.abac.component.supper;

import com.zjj.autoconfigure.component.security.abac.PolicyRule;
import com.zjj.security.abac.component.ExpressionUtils;
import com.zjj.security.abac.component.ObjectReturn;
import com.zjj.security.abac.component.annotation.AbacPreAuthorize;
import com.zjj.security.abac.component.object.AuthorizeObjectManager;
import io.vavr.Tuple2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.method.MethodInvocationResult;
import org.springframework.security.core.Authentication;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月12日 20:08
 */
@Slf4j
@RequiredArgsConstructor // TODO MethodAuthorizationDeniedHandler
public final class AbacPostAuthorizationManager
        implements AuthorizationManager<MethodInvocationResult> {

    private final AbacPostAuthorizeExpressionAttributeRegistry registry;
    private final AuthorizeObjectManager manager;
    private final SpelExpressionParser spelExpressionParser = new SpelExpressionParser();

    /**
     * Determines if access is granted for a specific authentication and object.
     *
     * @param authentication the {@link Supplier} of the {@link Authentication} to check
     * @param mi         the {@link MethodInvocationResult} object to check
     * @return an {@link AuthorizationDecision} or null if no decision could be made
     * @deprecated please use {@link #authorize(Supplier, Object)} instead
     */
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, MethodInvocationResult mi) {
        if (authentication.get() != null && "superAdmin".equals(authentication.get().getName())) {
            return new AuthorizationDecision(true);
        }
        PolicyRule policyRule = this.registry.getPolicyRule(authentication, mi.getMethodInvocation());

        if (policyRule == null || policyRule.isEmpty()) {
            log.warn("{} :policyRule is null", mi.getMethodInvocation().getMethod().toGenericString());
            return null;
        }

        if (Boolean.FALSE.equals(policyRule.getEnable())) {
            log.warn("{} :policyRule is not enable", mi.getMethodInvocation().getMethod().toGenericString());
            return null;
        }

        MethodSecurityExpressionHandler expressionHandler = this.registry.getExpressionHandler();
//        Map<String, Object> objects = this.registry.getObjectAttribute(mi.getMethodInvocation());
        EvaluationContext context = this.registry.getExpressionHandler().createEvaluationContext(authentication, mi.getMethodInvocation());
        expressionHandler.setReturnObject(mi.getResult(), context);
        Method method = mi.getMethodInvocation().getMethod();
        AbacPreAuthorize annotation = AnnotationUtils.findAnnotation(method, AbacPreAuthorize.class);
        ObjectReturn[] objectReturns = annotation.objectReturns();
        if (objectReturns != null) {
            for (ObjectReturn objectReturn : objectReturns) {
                String key = objectReturn.value();
                String name = objectReturn.name();
                String[] argument = objectReturn.argument();
                Object o = manager.processObject(key, name, mi.getMethodInvocation(), argument);
                context.setVariable(name, o);
            }
        }

        return (AuthorizationDecision) ExpressionUtils.evaluate(spelExpressionParser.parseExpression(policyRule.getCondition()), context);
    }

}
