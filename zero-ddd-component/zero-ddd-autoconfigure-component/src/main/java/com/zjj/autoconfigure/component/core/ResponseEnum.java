package com.zjj.autoconfigure.component.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月05日 21:16
 */
@Getter
@RequiredArgsConstructor
public enum ResponseEnum implements BaseEnum {
    SUCCESS(200, "成功"),
    FAIL(500, "失败"),
    WARN(601, "警告"),
    ;

    private final Integer code;
    private final String message;
}
