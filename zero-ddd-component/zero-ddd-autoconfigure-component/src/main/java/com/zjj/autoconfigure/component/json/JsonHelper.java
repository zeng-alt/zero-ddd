package com.zjj.autoconfigure.component.json;

import com.zjj.autoconfigure.component.UtilException;
import org.springframework.context.ApplicationContextAware;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月26日 18:20
 */
public interface JsonHelper {

	public String toJsonString(Object object) throws UtilException;

	public <T> T parseObject(String text, Class<T> clazz) throws UtilException;

	public <T> T parseObject(byte[] bytes, Class<T> clazz) throws UtilException;

	public <T> List<T> parseArray(String text, Class<T> clazz) throws UtilException;

}
