package com.zjj.excel.component.domain;


import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年07月05日 20:52
 */
@FunctionalInterface
public interface ExcelFailResult {

    List<String> getErrorList();
}
