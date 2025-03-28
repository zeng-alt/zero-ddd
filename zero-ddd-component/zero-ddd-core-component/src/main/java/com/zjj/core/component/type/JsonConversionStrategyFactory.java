package com.zjj.core.component.type;

import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月27日 16:59
 */
@Component
public class JsonConversionStrategyFactory {

    private final Map<String, List<JsonConversionStrategy>> converMap;

    private static final JsonConversionStrategy NO_STRATEGY = new JsonConversionStrategy() {
        @Override
        public String getType() {
            return null;
        }

        @Override
        public <T> T convert(String json) {
            return (T) json;
        }
    };

    public JsonConversionStrategyFactory(ObjectProvider<JsonConversionStrategy> jsonConversionStrategies) {
        this.converMap = jsonConversionStrategies
                            .orderedStream()
                            .peek(j -> Assert.notNull(j.getType(), j + " type not null"))
                            .collect(Collectors.groupingByConcurrent(JsonConversionStrategy::getType));
    }

    public JsonConversionStrategy getStrategy(TargetType targetType) {
        return getStrategy(targetType.getType());
    }

    public JsonConversionStrategy getStrategy(String targetType) {
        if (!converMap.containsKey(targetType)) {
            return NO_STRATEGY;
        }

        return converMap.get(targetType).stream()
                        .filter(j -> StringUtils.isNotBlank(j.getType()))
                        .findFirst()
                        .orElse(NO_STRATEGY);
    }

    public Set<String> getTypes() {
        return new HashSet<>(converMap.keySet());
    }
}
