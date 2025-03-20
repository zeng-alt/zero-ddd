package com.zjj.tenant.database.component.exception;

import com.zjj.i18n.component.BaseI18nException;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月23日 16:05
 */
public class TenantDataSourceException extends BaseI18nException {

    public TenantDataSourceException(String messageKey, String message) {
        super(messageKey, message);
    }

    public TenantDataSourceException(String message, Object... args) {
        super(message, message, args);
    }
}
