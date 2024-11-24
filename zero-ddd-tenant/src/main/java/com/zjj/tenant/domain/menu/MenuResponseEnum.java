package com.zjj.tenant.domain.menu;

import com.zjj.autoconfigure.component.core.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月11日 16:30
 */
@Getter
@RequiredArgsConstructor
public enum MenuResponseEnum implements BaseEnum {
    MENU_NOT_EXIST(5000_01, "MenuResourceHandler.handler");

    private final Integer code;
    private final String message;
}
