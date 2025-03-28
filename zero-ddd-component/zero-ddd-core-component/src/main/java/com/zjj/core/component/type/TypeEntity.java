package com.zjj.core.component.type;

import com.zjj.core.component.type.validation.TargetTypeValidation;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月28日 21:06
 */
public interface TypeEntity {

    @TargetTypeValidation
    String getType();

    String getValue();

    default <T> T getTargetValue() {
        return TypeConversionHelper.convert(getType(), getValue());
    }
}
