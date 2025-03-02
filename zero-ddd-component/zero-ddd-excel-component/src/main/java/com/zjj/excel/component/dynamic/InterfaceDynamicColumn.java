package com.zjj.excel.component.dynamic;

import com.zjj.i18n.component.MessageSourceHelper;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年02月28日 15:28
 */
public interface InterfaceDynamicColumn<T extends InterfaceDynamicEntity> {

    boolean add(T t);

    boolean remove(Object o);

    public List<T> getDynamicEntity();

    default List<List<String>> getData() {
        return getDynamicEntity()
                .stream()
                .map(InterfaceDynamicEntity::getValue)
                .map(List::of)
                .toList();
    }


    default List<List<String>> getHeads() {
        return getDynamicEntity()
                .stream()
                .map(InterfaceDynamicEntity::getNameTemplate)
                .map(s -> {
                    if (s.startsWith("{") && s.endsWith("}")) {
                        return MessageSourceHelper.getMessage(s, s);
                    }
                    return s;
                })
                .map(List::of)
                .toList();
    }
}
