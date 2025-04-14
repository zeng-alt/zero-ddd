package com.zjj.main.infrastructure.db.jpa.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月10日 14:55
 */

@Converter(autoApply = false) // 手动指定在哪些字段上使用
public class ExpressionConverter implements AttributeConverter<Expression, String> {

    private static final SpelExpressionParser parser = new SpelExpressionParser();

    @Override
    public String convertToDatabaseColumn(Expression attribute) {
        return attribute != null ? attribute.getExpressionString() : null;
    }

    @Override
    public Expression convertToEntityAttribute(String dbData) {
        return dbData != null ? parser.parseExpression(dbData) : null;
    }
}
