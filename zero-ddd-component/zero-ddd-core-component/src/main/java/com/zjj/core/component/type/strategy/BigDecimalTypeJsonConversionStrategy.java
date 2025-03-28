package com.zjj.core.component.type.strategy;

import com.zjj.core.component.type.JsonConversionStrategy;
import com.zjj.core.component.type.TargetType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月27日 17:16
 */
@Component
public class BigDecimalTypeJsonConversionStrategy extends JsonConversionStrategy {

    @Override
    public @NonNull <T> T convert(String jsonString) throws Exception {
        return (T) new BigDecimal(jsonString);
    }

    @Override
    public @NonNull String getType() {
        return TargetType.BIG_DECIMAL.getType();
    }
}
