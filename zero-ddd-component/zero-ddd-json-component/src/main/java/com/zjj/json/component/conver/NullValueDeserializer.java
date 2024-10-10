package com.zjj.json.component.conver;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.cache.support.NullValue;

import java.io.IOException;

public class NullValueDeserializer extends StdDeserializer<NullValue> {

		public static final NullValueDeserializer INSTANCE = new NullValueDeserializer();

		protected NullValueDeserializer() {
			super(NullValue.class);
		}

		@Override
		public NullValue deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
			return (NullValue) NullValue.INSTANCE;
		}
		

	}