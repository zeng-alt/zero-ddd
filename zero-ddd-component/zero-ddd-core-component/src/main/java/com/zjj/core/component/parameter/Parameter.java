package com.zjj.core.component.parameter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zjj.core.component.type.TypeConversionHelper;
import lombok.Data;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月09日 15:36
 */
@Data
public class Parameter {

    private String parameterName;
    private String parameterKey;
    private String parameterValue;
    private String parameterType;

    @JsonIgnore
    public <T> T getValue() {
        return TypeConversionHelper.convert(parameterType, parameterValue);
    }
}
