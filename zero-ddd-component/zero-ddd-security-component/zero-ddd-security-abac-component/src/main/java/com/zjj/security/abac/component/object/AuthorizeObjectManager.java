package com.zjj.security.abac.component.object;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月04日 10:51
 */
@Slf4j
public class AuthorizeObjectManager {

    private final Map<String, AuthorizeObjectMethodAdapter> methodMap = new ConcurrentHashMap<>();
    private final Map<String, List<Expression>> expressionMap = new ConcurrentHashMap<>();

    private final SpelExpressionParser parser = new SpelExpressionParser();
    private final DefaultParameterNameDiscoverer paramNameDiscoverer = new DefaultParameterNameDiscoverer();


    public void put(String key, AuthorizeObjectMethodAdapter applicationListener) {
        methodMap.put(key, applicationListener);
    }

    public Object processObject(String key, Object... args) {
        AuthorizeObjectMethodAdapter applicationListener = methodMap.get(key);
        if (applicationListener != null) {
            return applicationListener.processObject(args);
        }
        log.warn("没有找到对应的方法 {}", key);
        return null;
    }

    public Object processObject(String key, String name, MethodInvocation mi, String[] args) {
        String[] paramNames = paramNameDiscoverer.getParameterNames(mi.getMethod());
        if (paramNames == null || paramNames.length == 0 || args == null || args.length == 0) {
            this.processObject(key);
        }

        List<Expression> expressions = expressionMap.get(key + ":" + name);
        if (CollectionUtils.isEmpty(expressions)) {
            this.registerExpressions(key + ":" + name, args);
            expressions = expressionMap.get(key + ":" + name);
        }

        // 将 SpEL 表达式绑定到参数值上
        StandardEvaluationContext context = new StandardEvaluationContext();
        Object[] arguments = mi.getArguments();
        for (int i = 0; i < paramNames.length; i++) {
            context.setVariable(paramNames[i], arguments[i]);
        }

        List<Object> results = new ArrayList<>();
        for (Expression expression : expressions) {
            Object value = expression.getValue(context);
            results.add(value);
        }

        // 你可以根据业务返回结果，例如返回第一个，或整个 list
        return this.processObject(key, results.toArray());
    }

    public synchronized void registerExpressions(String name, String[] exprStrings) {
        List<Expression> expressions = new ArrayList<>();
        for (String expr : exprStrings) {
            expressions.add(parser.parseExpression(expr));
        }
        expressionMap.put(name, expressions);
    }
}
