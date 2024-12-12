package com.zjj.security.abac.component.supper;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.io.IOException;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月12日 21:23
 */
public class SpelDeserializer extends StdDeserializer<Expression> {

    ExpressionParser elParser = new SpelExpressionParser();

    public SpelDeserializer() {
        super(Expression.class);
    }

    @Override
    public Expression deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String expresionString = jsonParser.getCodec().readValue(jsonParser, String.class);
        return elParser.parseExpression(expresionString);
    }
}
