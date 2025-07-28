package com.zjj.security.abac.component.object;

import com.zjj.autoconfigure.component.security.abac.AbacContextEntity;
import com.zjj.autoconfigure.component.security.abac.ContextEntity;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年07月02日 14:29
 */
public interface AbacMappingHandlerMapping<T extends ContextEntity> {

    public void register(String name, T abacContextEntity);

    public void send();
}
