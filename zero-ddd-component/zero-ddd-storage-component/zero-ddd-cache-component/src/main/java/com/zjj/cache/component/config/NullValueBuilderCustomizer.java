package com.zjj.cache.component.config;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.cache.support.NullValue;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

public final class NullValueBuilderCustomizer implements Jackson2ObjectMapperBuilderCustomizer {

    public static final NullValueBuilderCustomizer INSTANCE = new NullValueBuilderCustomizer();

    @Override
    public void customize(Jackson2ObjectMapperBuilder builder) {
        builder.mixIn(NullValue.class, UseTypeInfo.class);
        builder.deserializers(NullValueDeserializer.INSTANCE);
    }

    public static class NullValueDeserializer extends StdDeserializer<NullValue> {

        public static final NullValueDeserializer INSTANCE = new NullValueDeserializer();

        protected NullValueDeserializer() {
            super(NullValue.class);
        }

        @Override
        public NullValue deserialize(JsonParser p, DeserializationContext ctx) {
            return (NullValue) NullValue.INSTANCE;
        }
    }

}

// Mix-in annotation to apply only to record classes
