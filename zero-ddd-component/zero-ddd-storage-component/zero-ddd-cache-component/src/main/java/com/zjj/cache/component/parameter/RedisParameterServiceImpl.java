package com.zjj.cache.component.parameter;

import com.zjj.autoconfigure.component.redis.RedisStringRepository;
import com.zjj.core.component.parameter.Parameter;
import com.zjj.core.component.parameter.ParameterService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月09日 15:42
 */
@RequiredArgsConstructor
public class RedisParameterServiceImpl implements ParameterService {

    public final RedisStringRepository redisStringRepository;

    @Override
    public Parameter getParameter(String parameterKey) {
        return redisStringRepository.get(PARAMETER_KEY + getTenantKey() + parameterKey);
    }

    @Override
    public void putParameter(Parameter parameter) {
        redisStringRepository.put(PARAMETER_KEY + getTenantKey() + parameter.getParameterKey(), parameter);
    }

    @Override
    public void batchPutParameter(List<Parameter> parameter) {
        redisStringRepository.batchPut(PARAMETER_KEY + getTenantKey(), parameter.stream().collect(Collectors.toMap(Parameter::getParameterKey, p -> p)));
    }
}
