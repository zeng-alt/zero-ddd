package com.zjj.excel.component.listener;

import cn.idev.excel.annotation.ExcelIgnore;
import cn.idev.excel.annotation.ExcelProperty;
import com.zjj.excel.component.dynamic.DynamicColumn;
import com.zjj.excel.component.dynamic.ExcelDynamicProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 基础数据类
 *
 * @author Jiaju Zhuang
 **/
@Getter
@Setter
@EqualsAndHashCode
public class DemoData {
    @ExcelProperty("字符串标题")
    private String string;
//    @ExcelProperty("日期标题")
//    private Date date;
//    @ExcelProperty("数字标题")
//    private Double doubleData;

    @ExcelDynamicProperty
    private DynamicColumn dynamicColumn;

    /**
     * 忽略这个字段
     */
    @ExcelIgnore
    private String ignore;
}
