package com.zjj.core.component.parameter;

import com.zjj.autoconfigure.component.tenant.TenantKey;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月09日 15:36
 */
public interface ParameterService extends TenantKey {

    public static final String PARAMETER_KEY = "system:parameter:";


    public Parameter getParameter(String parameterKey);

    public void putParameter(Parameter parameter);

    public void batchPutParameter(List<Parameter> parameter);
}
