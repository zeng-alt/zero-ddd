package com.zjj.tenant.management.component.service;

public class TenantCreationException extends RuntimeException {

    public TenantCreationException(String message) {
        super(message);
    }

    public TenantCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public TenantCreationException(Throwable cause) {
        super(cause);
    }
}