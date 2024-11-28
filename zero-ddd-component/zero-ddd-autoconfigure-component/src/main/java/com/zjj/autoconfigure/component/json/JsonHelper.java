package com.zjj.autoconfigure.component.json;

import com.zjj.autoconfigure.component.UtilException;

import java.util.List;
import java.util.Map;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月26日 18:20
 */
public interface JsonHelper {

	public String toJsonString(Object object) throws UtilException;

	public Map<String, Object> toMap(Object object) throws UtilException;

	public <T> T parseObject(String text, Class<T> clazz) throws UtilException;

	public <T> T parseObject(byte[] bytes, Class<T> clazz) throws UtilException;

	public <T> List<T> parseArray(String text, Class<T> clazz) throws UtilException;

}
