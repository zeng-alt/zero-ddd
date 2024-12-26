package com.zjj.tenant.management.component.service;

import com.zjj.i18n.component.BaseI18nException;

public class TenantCreationException extends BaseI18nException {

    public TenantCreationException(String message) {
        super(message);
    }

    public TenantCreationException(String message, Throwable cause) {
        super(message, cause.getMessage());
    }

    public TenantCreationException(Throwable cause) {
        super(cause);
    }
}