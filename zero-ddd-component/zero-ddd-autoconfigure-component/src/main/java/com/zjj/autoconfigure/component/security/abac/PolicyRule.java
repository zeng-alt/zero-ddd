package com.zjj.autoconfigure.component.security.abac;

import lombok.Getter;
import lombok.Setter;
import org.springframework.expression.Expression;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月12日 21:29
 */
@Getter
@Setter
public class PolicyRule {
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