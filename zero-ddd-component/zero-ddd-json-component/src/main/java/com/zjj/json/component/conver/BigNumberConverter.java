package com.zjj.json.component.conver;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.ser.std.NumberSerializer;

import java.io.IOException;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年07月05日 20:46
 */
@JacksonStdImpl
public class BigNumberConverter extends NumberSerializer {

	private static final long MAX_SAFE_INTEGER = 9007199254740991L;

	private static final long MIN_SAFE_INTEGER = -9007199254740991L;

	public static final BigNumberConverter INSTANCE = new BigNumberConverter(Number.class);

	public BigNumberConverter(Class<? extends Number> rawType) {
		super(rawType);
	}

	@Override
	public void serialize(Number value, JsonGenerator g, SerializerProvider provider) throws IOException {
		if (value.longValue() > MIN_SAFE_INTEGER && value.longValue() < MAX_SAFE_INTEGER) {
			super.serialize(value, g, provider);
		}
		else {
			g.writeString(value.toString());
		}
	}

	// @Override
	// public JsonElement serialize(Number number, Type type, JsonSerializationContext
	// jsonSerializationContext) {
	// if (number.longValue() > MIN_SAFE_INTEGER && number.longValue() < MAX_SAFE_INTEGER)
	// {
	// return jsonSerializationContext.serialize(number);
	// }
	// return jsonSerializationContext.serialize(number.toString());
	// }

}
