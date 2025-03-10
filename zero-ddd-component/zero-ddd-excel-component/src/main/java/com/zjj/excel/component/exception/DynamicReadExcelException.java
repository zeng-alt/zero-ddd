package com.zjj.excel.component.exception;

import com.zjj.i18n.component.BaseI18nException;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月10日 21:50
 */
public class DynamicReadExcelException extends BaseI18nException {

    public DynamicReadExcelException(String message) {
        super(message);
    }

    public DynamicReadExcelException(String code, String message) {
        super(code, message);
    }
}
