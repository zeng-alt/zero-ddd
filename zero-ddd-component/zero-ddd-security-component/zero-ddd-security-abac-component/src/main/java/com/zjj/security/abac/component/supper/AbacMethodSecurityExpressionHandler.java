package com.zjj.security.abac.component.supper;

import com.zjj.security.abac.component.spi.EnvironmentAttribute;
import com.zjj.security.abac.component.spi.ObjectAttribute;
import com.zjj.security.abac.component.spi.SubjectAttribute;
import lombok.Getter;
import lombok.Setter;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.security.access.expression.SecurityExpressionOperations;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月12日 21:34
 */
@Getter
@Setter
public class AbacMethodSecurityExpressionHandler extends AbstractAbacSecurityExpressionHandler<MethodInvocation>
        implements MethodSecurityExpressionHandler {

    private SubjectAttribute subjectAttribute;
    private List<EnvironmentAttribute> environmentAttributes = new ArrayList<>();
    private AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();
    private String defaultRolePrefix = "";


    @Override
    public Object filter(Object filterTarget, Expression filterExpression, EvaluationContext ctx) {
        return null;
    }

    @Override
    public void setReturnObject(Object returnObject, EvaluationContext ctx) {

    }

    @Override
    protected SecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication, MethodInvocation invocation) {
        return createSecurityExpressionRoot(() -> authentication, invocation);
    }

    private MethodSecurityExpressionOperations createSecurityExpressionRoot(Supplier<Authentication> authentication,
                                                                            MethodInvocation invocation) {
        AbacSecurityExpressionRoot root = new AbacSecurityExpressionRoot(authentication);
        root.setThis(invocation.getThis());
        root.setPermissionEvaluator(getPermissionEvaluator());
        root.setTrustResolver(getTrustResolver());
        root.setRoleHierarchy(getRoleHierarchy());
        root.setDefaultRolePrefix(getDefaultRolePrefix());
        Mono<Object> subject = subjectAttribute == null ? Mono.empty() : Mono.create(sink -> sink.success(subjectAttribute.getSubject(authentication.get())));
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
}
