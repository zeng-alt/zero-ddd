package com.zjj.excel.component.dynamic;

import lombok.Data;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年02月28日 15:26
 */
public interface InterfaceDynamicEntity {

    Integer getIndex();

    String getName();

    String getNameTemplate();

    String getValue();
}
