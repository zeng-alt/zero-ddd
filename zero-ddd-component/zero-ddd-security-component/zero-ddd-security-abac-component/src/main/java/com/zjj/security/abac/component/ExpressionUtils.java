package com.zjj.security.abac.component;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.authorization.ExpressionAuthorizationDecision;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月17日 21:55
 */
public class ExpressionUtils {
    private ExpressionUtils() {

    }

    public static AuthorizationResult evaluate(Expression expr, EvaluationContext ctx) {
        try {
            Object result = expr.getValue(ctx);
            if (result instanceof AuthorizationResult decision) {
                return decision;
            }
            if (result instanceof Boolean granted) {
                return new ExpressionAuthorizationDecision(granted, expr);
            }
            if (result == null) {
                return null;
            }
            throw new IllegalArgumentException(
                    "SpEL expression must return either a Boolean or an AuthorizationDecision");
        }
        catch (EvaluationException ex) {
            throw new IllegalArgumentException("Failed to evaluate expression '" + expr.getExpressionString() + "'",
                    ex);
        }
    }


    public static boolean evaluateAsBoolean(Expression expr, EvaluationContext ctx) {
        try {
            return expr.getValue(ctx, Boolean.class);
        }
        catch (EvaluationException ex) {
            throw new IllegalArgumentException("Failed to evaluate expression '" + expr.getExpressionString() + "'",
                    ex);
        }
    }
}
