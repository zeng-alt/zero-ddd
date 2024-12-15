package com.zjj.security.abac.component.supper;

import com.zjj.autoconfigure.component.security.abac.AbacCacheManage;
import com.zjj.autoconfigure.component.security.abac.PolicyRule;
import com.zjj.autoconfigure.component.tenant.TenantDetail;
import com.zjj.security.abac.component.annotation.AbacPreAuthorize;
import io.vavr.Tuple2;
import org.springframework.lang.NonNull;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authorization.method.MethodAuthorizationDeniedHandler;
import org.springframework.security.authorization.method.ThrowingMethodAuthorizationDeniedHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.SecurityAnnotationScanner;
import org.springframework.security.core.annotation.SecurityAnnotationScanners;

import java.lang.reflect.Method;
import java.util.function.Supplier;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月13日 21:38
 */
public class AbacPreAuthorizeExpressionAttributeRegistry extends AbstractAbacExpressionAttributeRegistry<PolicyRule> {

    private final MethodAuthorizationDeniedHandler defaultHandler = new ThrowingMethodAuthorizationDeniedHandler();
    private final AbacCacheManage abacCacheManage;

    private SecurityAnnotationScanner<AbacPreAuthorize> preAuthorizeScanner = SecurityAnnotationScanners
            .requireUnique(AbacPreAuthorize.class);

    public AbacPreAuthorizeExpressionAttributeRegistry(MethodSecurityExpressionHandler expressionHandler, AbacCacheManage abacCacheManage) {
        super(expressionHandler);
        this.abacCacheManage = abacCacheManage;
    }


    @Override
    protected PolicyRule resolvePolicyRule(Supplier<Authentication> authentication, String policyKey, String resourceType) {
        String tenant = null;
        if (authentication.get() != null) {
            Authentication auth = authentication.get();
            Object principal = auth.getPrincipal();
            if (principal instanceof TenantDetail tenantDetail) {
                tenant = tenantDetail.getTenantName();
            }
        }

        return abacCacheManage.getRule(tenant, policyKey, resourceType);
    }

    @NonNull
    @Override
    protected Tuple2<String, String> resolvePolicyKey(Method method, Class<?> targetClass) {
        AbacPreAuthorize annotation = findAbacPreAuthorizeAnnotation(method, targetClass);
        if (annotation == null) {
            throw new IllegalStateException("Unable to resolve policy rule for method '" + method.getName() + "'");
        }
        return new Tuple2<>(annotation.value(), annotation.resourceType());
    }

    private AbacPreAuthorize findAbacPreAuthorizeAnnotation(Method method, Class<?> targetClass) {
        Class<?> targetClassToUse = targetClass(method, targetClass);
        return this.preAuthorizeScanner.scan(method, targetClassToUse);
    }
}
