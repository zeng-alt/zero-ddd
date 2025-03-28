package com.zjj.core.component.type.strategy;

import com.zjj.autoconfigure.component.json.JsonHelper;
import com.zjj.core.component.type.JsonConversionStrategy;
import com.zjj.core.component.type.TargetType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月27日 17:16
 */
@RequiredArgsConstructor
public class ListTypeJsonConversionStrategy extends JsonConversionStrategy {

    private final JsonHelper jsonHelper;

    @Override
    public @NonNull <T> T convert(String jsonString) throws Exception {
        return (T) jsonHelper.parseObject(jsonString, List.class);
    }

    @Override
    public @NonNull String getType() {
        return TargetType.LIST.getType();
    }
}
