package com.zjj.security.abac.component.object;


import com.zjj.autoconfigure.component.security.abac.AbacContextEntity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月10日 16:05
 */
public class AbacMappingHandlerMapping {

    private final Map<String, AbacContextEntity> nameLookup = new ConcurrentHashMap<>();


    public void register(String name, AbacContextEntity abacContextEntity) {
        this.nameLookup.put(name, abacContextEntity);
    }

    public void send() {}
}
