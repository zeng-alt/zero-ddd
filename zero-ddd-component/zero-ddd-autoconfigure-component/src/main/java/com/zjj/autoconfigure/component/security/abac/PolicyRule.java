package com.zjj.autoconfigure.component.security.abac;

import lombok.Getter;
import lombok.Setter;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.CompositeStringExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月12日 21:29
 */
@Getter
@Setter
public class PolicyRule {

	public static final PolicyRule TRUE_RULE
			= new PolicyRule("TRUE_RULE", "TRUE_RULE", new SpelExpressionParser().parseExpression("true"));

	public static final PolicyRule ADMIN_AUTH_RULE
			= new PolicyRule("ADMIN_AUTH_RULE", "ADMIN_AUTH_RULE", new SpelExpressionParser().parseExpression("authentication.principal.currentRole == 'admin'"));

	public static final PolicyRule MASTER_ADMIN_AUTH_RULE
			= new PolicyRule("MASTER_ADMIN_AUTH_RULE", "MASTER_ADMIN_AUTH_RULE", new SpelExpressionParser().parseExpression("authentication.principal.currentRole == 'admin' && authentication.principal.tenant == 'master'"));

	public static final PolicyRule SUPER_ADMIN_AUTH_RULE
			= new PolicyRule("SUPER_ADMIN_AUTH_RULE", "SUPER_ADMIN_AUTH_RULE", new SpelExpressionParser().parseExpression("authentication.principal.username == 'superAdmin'"));

	public static final PolicyRule MASTER_SUPER_ADMIN_AUTH_RULE
			= new PolicyRule("MASTER_SUPER_ADMIN_AUTH_RULE", "MASTER_SUPER_ADMIN_AUTH_RULE", new SpelExpressionParser().parseExpression("authentication.principal.username == 'superAdmin' && authentication.principal.tenant == 'master'"));
	private String name;
	private String description;
	
	/*
	 * Boolean SpEL expression, if evaluated to true, then access granted.
	 */
	private Expression  condition;
	
	public PolicyRule() {
		
	}

	public PolicyRule(String name, String description, Expression condition) {
		this(condition);
		this.name = name;
		this.description = description;
	}

	public PolicyRule(Expression condition) {
		super();
		this.condition = condition;
	}
}