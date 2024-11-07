package com.zjj.i18n.component;

import com.zjj.autoconfigure.component.core.BaseException;

/**
 * 支持带参数的异常做国际化
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月07日 21:19
 */
public class BaseI18nException extends BaseException {

    public BaseI18nException(String message) {
        super(MessageSourceHelper.getMessage(message, message));
    }

    public BaseI18nException(Throwable cause) {
        super(cause);
    }

    public BaseI18nException(String message, Object... args) {
        super(MessageSourceHelper.getMessage(message, message, args));
    }

    public BaseI18nException(String code, String defaultMessage, Object... args) {
        super(MessageSourceHelper.getMessage(code, defaultMessage, args));
    }
}
