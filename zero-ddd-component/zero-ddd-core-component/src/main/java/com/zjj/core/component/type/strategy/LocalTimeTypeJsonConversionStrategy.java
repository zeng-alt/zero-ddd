package com.zjj.core.component.type.strategy;

import com.zjj.core.component.type.JsonConversionStrategy;
import com.zjj.core.component.type.TargetType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月27日 17:16
 */
@Component
public class LocalTimeTypeJsonConversionStrategy extends JsonConversionStrategy {


    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public @NonNull <T> T convert(String jsonString) throws Exception {
        return (T) LocalTime.parse(jsonString, FORMATTER);
    }

    @Override
    public @NonNull String getType() {
        return TargetType.LOCALTIME.getType();
    }
}
