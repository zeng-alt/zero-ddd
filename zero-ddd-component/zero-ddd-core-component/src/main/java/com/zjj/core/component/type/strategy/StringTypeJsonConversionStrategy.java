package com.zjj.core.component.type.strategy;

import com.zjj.core.component.type.JsonConversionStrategy;
import com.zjj.core.component.type.TargetType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月27日 17:16
 */
@Component
public class StringTypeJsonConversionStrategy extends JsonConversionStrategy {

    @Override
    public @NonNull <T> T convert(String jsonString) throws Exception {
        return (T) jsonString;
    }

    @Override
    public @NonNull String getType() {
        return TargetType.STRING.getType();
    }
}
