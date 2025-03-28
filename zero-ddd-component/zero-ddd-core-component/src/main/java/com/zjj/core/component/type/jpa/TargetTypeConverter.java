package com.zjj.core.component.type.jpa;

import com.zjj.core.component.type.JsonConversionStrategy;
import com.zjj.core.component.type.TypeConversionHelper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月28日 11:12
 */
@Converter(autoApply = true)
public class TargetTypeConverter implements AttributeConverter<JsonConversionStrategy, String> {


    @Override
    public String convertToDatabaseColumn(JsonConversionStrategy attribute) {
        return attribute.getType();
    }

    @Override
    public JsonConversionStrategy convertToEntityAttribute(String dbData) {
        return TypeConversionHelper.getStrategy(dbData);
    }
}
