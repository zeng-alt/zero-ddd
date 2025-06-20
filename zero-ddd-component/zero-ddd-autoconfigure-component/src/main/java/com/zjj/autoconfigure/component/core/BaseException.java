package com.zjj.autoconfigure.component.core;

import lombok.Getter;
import org.springframework.lang.NonNull;

import java.util.function.IntSupplier;
import java.util.function.Supplier;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月07日 21:15
 */
@Getter
public class BaseException extends RuntimeException {

    private Integer code = 400;


    public BaseException(String message) {
        super(message);
    }

    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BaseException(@NonNull IntSupplier code, Supplier<String> message) {
        super(message.get());
        this.code = code.getAsInt();
    }

    public BaseException(@NonNull BaseEnum baseEnum) {
        super(baseEnum.getMessage());
        this.code = baseEnum.getCode();
    }

    public BaseException(Throwable cause) {
        super(cause);
    }
}
