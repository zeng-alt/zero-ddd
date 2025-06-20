package com.zjj.tenant.infrastructure.exception;

import com.zjj.i18n.component.BaseI18nException;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月06日 11:21
 */
public class DataSourceConnectionException extends BaseI18nException {

    public DataSourceConnectionException(String messageDetail) {
        super("DataSourceConnectionException", "数据源连接异常", messageDetail);
    }
}
