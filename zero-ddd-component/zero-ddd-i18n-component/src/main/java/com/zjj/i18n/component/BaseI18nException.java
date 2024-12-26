package com.zjj.i18n.component;

import com.zjj.autoconfigure.component.core.BaseEnum;
import com.zjj.autoconfigure.component.core.BaseException;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

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

    public BaseI18nException(String messageKey, String message) {
        super(MessageSourceHelper.getMessage(messageKey, message));
    }

    public BaseI18nException(Throwable cause) {
        super(cause);
    }

    public BaseI18nException(String messageKey, String message, Object... args) {
        super(MessageSourceHelper.getMessage(messageKey, message, args));
    }


    public BaseI18nException(Integer code, String message, Object... args) {
        super(code, MessageSourceHelper.getMessage(message, message, args));
    }

    public BaseI18nException(@NonNull IntSupplier code, Supplier<String> message) {
        this(code.getAsInt(), message.get());
    }

    public BaseI18nException(@NonNull BaseEnum baseEnum) {
        this(baseEnum.getCode(), baseEnum.getMessage());
    }


    public BaseI18nException(@NonNull IntSupplier code, Supplier<String> message, Object... args) {
        this(code.getAsInt(), message.get(), args);
    }

    public BaseI18nException(@NonNull BaseEnum baseEnum, Object... args) {
        this(baseEnum.getCode(), baseEnum.getMessage(), args);
    }

//    public BaseI18nException(String code, String defaultMessage, Object... args) {
//        super(MessageSourceHelper.getMessage(code, defaultMessage, args));
//    }
}
