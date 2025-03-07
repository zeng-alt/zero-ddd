package com.zjj.excel.component.i18n;

import cn.idev.excel.annotation.ExcelIgnore;
import cn.idev.excel.annotation.ExcelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月03日 10:03
 */
@Data
public class I18nData {

    @NotNull
    @ExcelProperty("字符串标题")
    private String stringt;
    @ExcelProperty("{writeData.string}")
    private String string;
    @ExcelProperty("{writeData.string}")
    private String string1;
    @ExcelProperty("{writeData.date}")
    private Date date;
    @ExcelProperty("{writeData.doubleData}")
    private Double doubleData;
    /**
     * 忽略这个字段
     */
    @ExcelIgnore
    private String ignore;
}
