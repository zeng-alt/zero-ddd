package com.zjj.graphql.component.context;

import graphql.language.Type;
import graphql.language.TypeName;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月22日 20:54
 */
public class ConditionTypeContext {

    private final Map<String, Type> types = new ConcurrentHashMap<>();


    public void addType(String typeName) {
        types.put(typeName, TypeName.newTypeName(typeName).build());
    }


    public void addType(String typeName, Type type) {
        types.put(typeName, type);
    }

    public Type get(String typeName) {
        return types.get(typeName);
    }
}
