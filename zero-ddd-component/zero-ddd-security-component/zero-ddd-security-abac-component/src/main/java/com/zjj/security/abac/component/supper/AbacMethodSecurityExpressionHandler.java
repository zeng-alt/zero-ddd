package com.zjj.security.abac.component.supper;

import com.zjj.autoconfigure.component.security.abac.EnvironmentAttribute;
import com.zjj.autoconfigure.component.security.abac.SubjectAttribute;
import com.zjj.autoconfigure.component.tenant.TenantDetail;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.security.access.expression.SecurityExpressionOperations;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import org.springframework.util.function.SupplierUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.function.Supplier;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月12日 21:34
 */
@Slf4j
@Getter
@Setter
public class AbacMethodSecurityExpressionHandler extends AbstractAbacSecurityExpressionHandler<MethodInvocation>
                    implements MethodSecurityExpressionHandler {

    private SubjectAttribute subjectAttribute;
    private List<EnvironmentAttribute> environmentAttributes = new ArrayList<>();
    private AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();
    private String defaultRolePrefix = "";


    @Override
    protected SecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication, MethodInvocation invocation) {
        return createSecurityExpressionRoot(() -> authentication, invocation);
    }

    // TODO 优化
    private MethodSecurityExpressionOperations createSecurityExpressionRoot(Supplier<Authentication> authentication,
                                                                            MethodInvocation invocation) {
        AbacSecurityExpressionRoot root = new AbacSecurityExpressionRoot(authentication);
        // 拿到方法的参数名
        HashMap<String, Object> target = new HashMap<>();
        for (int i = 0; i < invocation.getArguments().length; i++) {
            target.put(invocation.getMethod().getParameters()[i].getName(), invocation.getArguments()[i]);
        }
        root.setTarget(target);
        root.setPermissionEvaluator(getPermissionEvaluator());
        root.setTrustResolver(getTrustResolver());
        root.setRoleHierarchy(getRoleHierarchy());
        root.setDefaultRolePrefix(getDefaultRolePrefix());

        Authentication resolve = SupplierUtils.resolve(authentication);
        Object principal = resolve.getPrincipal();
        String username = Optional.ofNullable(principal)
                .filter(UserDetails.class::isInstance)
                .map(UserDetails.class::cast)
                .map(UserDetails::getUsername)
                .orElse(null);
        String tenant = Optional.ofNullable(principal)
                .filter(TenantDetail.class::isInstance)
                .map(TenantDetail.class::cast)
                .map(TenantDetail::getTenantName)
                .orElse(null);
        // TODO要拿到用户的个人信息
        Mono<Object> subject = Optional.ofNullable(subjectAttribute)
                .map(attr -> Mono.create(sink -> sink.success(attr.getSubject(username, tenant))))
                .orElse(Mono.empty());

        Flux<Map<String, Object>> mapFlux = Flux.create(fluxSink -> {
            for (EnvironmentAttribute environmentAttribute : environmentAttributes) {
                fluxSink.next(environmentAttribute.getEnvironment(authentication.get()));
            }
            fluxSink.complete();
        });


        subject.subscribe(root::setSubject);
        mapFlux.subscribe(root::addContext);

        return root;
    }

    public void setDefaultRolePrefix(String defaultRolePrefix) {
        this.defaultRolePrefix = defaultRolePrefix;
    }

    /**
     * @return The default role prefix
     */
    protected String getDefaultRolePrefix() {
        return this.defaultRolePrefix;
    }

    public void setTrustResolver(AuthenticationTrustResolver trustResolver) {
        Assert.notNull(trustResolver, "trustResolver cannot be null");
        this.trustResolver = trustResolver;
    }
    
    

    /**
     * @return The current {@link AuthenticationTrustResolver}
     */
    protected AuthenticationTrustResolver getTrustResolver() {
        return this.trustResolver;
    }


    @Override
    public Object filter(Object filterTarget, Expression filterExpression, EvaluationContext ctx) {
        return null;
    }


    @Override
    public void setReturnObject(Object returnObject, EvaluationContext ctx) {
        ((MethodSecurityExpressionOperations) ctx.getRootObject().getValue()).setReturnObject(returnObject);
    }
}
