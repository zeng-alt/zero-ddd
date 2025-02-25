package com.zjj.autoconfigure.component.tenant;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月23日 21:52
 */
public enum TenantMode {

    COLUMN,
    SCHEMA,
    DATABASE,

    /*************************
     * 分库分表未实现
     * ***********************
     */
    SHARDED_COLUMN,
    SHARDED_SCHEMA,
    SHARDED_DATABASE,
    ;

    private TenantMode() {}
}
