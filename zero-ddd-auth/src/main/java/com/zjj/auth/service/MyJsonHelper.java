package com.zjj.auth.service;

import com.zjj.autoconfigure.component.UtilException;
import com.zjj.autoconfigure.component.json.JsonHelper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年09月27日 09:43
 */
//@Component
public class MyJsonHelper implements JsonHelper {

    @Override
    public String toJsonString(Object object) throws UtilException {
        return "hello";
    }

    @Override
    public <T> T parseObject(String text, Class<T> clazz) throws UtilException {
        return null;
    }

    @Override
    public <T> T parseObject(byte[] bytes, Class<T> clazz) throws UtilException {
        return null;
    }

    @Override
    public <T> List<T> parseArray(String text, Class<T> clazz) throws UtilException {
        return null;
    }
}
