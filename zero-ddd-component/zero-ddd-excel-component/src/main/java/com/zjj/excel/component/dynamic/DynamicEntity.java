package com.zjj.excel.component.dynamic;

import lombok.Data;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年02月28日 15:29
 */
@Data
public class DynamicEntity implements InterfaceDynamicEntity {
    private Integer index;
    private String name;
    private String nameTemplate;
    private String value;
    private String type;
}
