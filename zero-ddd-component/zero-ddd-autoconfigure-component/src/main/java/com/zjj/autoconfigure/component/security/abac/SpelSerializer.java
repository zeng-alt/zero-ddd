package com.zjj.autoconfigure.component.security.abac;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.expression.Expression;

import java.io.IOException;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月10日 16:43
 */
public class SpelSerializer extends StdSerializer<Expression> {

    public SpelSerializer() {
        super(Expression.class);
    }

    @Override
    public void serialize(Expression expression, JsonGenerator gen, SerializerProvider provider) throws IOException, IOException {
        gen.writeString(expression.getExpressionString());
    }

    @Override
    public void serializeWithType(Expression value, JsonGenerator gen, SerializerProvider serializers,
                                  TypeSerializer typeSer) throws IOException {
        // 手动写入类型信息（@class），然后再调用正常的序列化逻辑
        WritableTypeId typeId = typeSer.writeTypePrefix(gen, typeSer.typeId(value, JsonToken.VALUE_STRING));
        serialize(value, gen, serializers);
        typeSer.writeTypeSuffix(gen, typeId);
    }
}
