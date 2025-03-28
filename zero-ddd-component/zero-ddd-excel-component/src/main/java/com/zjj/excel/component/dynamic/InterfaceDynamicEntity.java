package com.zjj.excel.component.dynamic;

import com.zjj.core.component.type.TypeEntity;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年02月28日 15:26
 */
public interface InterfaceDynamicEntity extends TypeEntity {

    /** excel列索引 */
    Integer getIndex();

    /** 默认名, 如果NameTemplate不为空将使用对于的国际化语言 */
    String getName();

    /** 国际化key */
    String getNameTemplate();
}
