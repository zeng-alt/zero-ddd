package com.zjj.main.domain.parameter;

import com.zjj.core.component.type.JsonConversionStrategy;
import com.zjj.domain.component.Aggregate;
import lombok.Data;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月28日 14:10
 */
@Data
public class ParameterAgg extends Aggregate<Long> {

    private Long id;

    private String parameterName;
    private String parameterKey;
    private String parameterValue;
    private JsonConversionStrategy parameterType;
    private String remark;

    public <T> T getTargetValue() {
        try {
            return parameterType.convert(parameterValue);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
