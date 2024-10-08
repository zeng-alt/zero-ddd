package com.zjj.json.component.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zjj.autoconfigure.component.UtilException;
import com.zjj.autoconfigure.component.json.JsonHelper;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年07月05日 20:40
 */
public final class JacksonHelper implements JsonHelper, ApplicationContextAware {

	private static ObjectMapper objectMapper;

	public JacksonHelper() {
	}

	@Override
	public String toJsonString(Object object) {
		if (ObjectUtils.isEmpty(object)) {
			return null;
		}
		try {
			return objectMapper.writeValueAsString(object);
		}
		catch (JsonProcessingException e) {
			throw new UtilException(e);
		}
	}

	@Override
	public <T> T parseObject(String text, Class<T> clazz) {
		if (!StringUtils.hasText(text)) {
			return null;
		}
		try {
			return objectMapper.readValue(text, clazz);
		}
		catch (IOException e) {
			throw new UtilException(e);
		}
	}

	@Override
	public <T> T parseObject(byte[] bytes, Class<T> clazz) {
		if (bytes == null || bytes.length == 0) {
			return null;
		}
		try {
			return objectMapper.readValue(bytes, clazz);
		}
		catch (IOException e) {
			throw new UtilException(e);
		}
	}

	@Override
	public <T> List<T> parseArray(String text, Class<T> clazz) {
		if (!StringUtils.hasText(text)) {
			return new ArrayList<>();
		}
		try {
			return objectMapper.readValue(text,
					objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
		}
		catch (IOException e) {
			throw new UtilException(e);
		}
	}

	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		objectMapper = applicationContext.getBean(ObjectMapper.class);
	}

}
