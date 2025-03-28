package com.zjj.core.component.type;

import com.zjj.autoconfigure.component.UtilException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月27日 17:00
 */
@Component
public class JsonConversionContext {

    private final JsonConversionStrategyFactory jsonConversionStrategyFactory;

    @Autowired
    public JsonConversionContext(JsonConversionStrategyFactory jsonConversionStrategyFactory) {
        this.jsonConversionStrategyFactory = jsonConversionStrategyFactory;
    }

    public <T> T convert(String type, String json) {
        try {
            return jsonConversionStrategyFactory.getStrategy(type).convert(json);
        } catch (Exception e) {
            throw new UtilException(e);
        }
    }

    public <T> T convert(TargetType type, String json) {
        return convert(type.getType(), json);
    }

    public Set<String> getTypes() {
        return jsonConversionStrategyFactory.getTypes();
    }

    public JsonConversionStrategy getStrategy(String type) {
        return jsonConversionStrategyFactory.getStrategy(type);
    }
}
