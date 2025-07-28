package com.zjj.autoconfigure.component.security.abac;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年07月02日 14:43
 */
public interface ContextEntity<T1, T2, T3> {

    public void setObjectEntities(T1 objectEntities);
    public void setResultObject(T2 resultObject);
    public void setArguments(T3 arguments);
}
