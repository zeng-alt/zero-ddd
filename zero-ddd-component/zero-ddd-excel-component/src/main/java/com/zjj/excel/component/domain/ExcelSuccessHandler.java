package com.zjj.excel.component.domain;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年07月23日 16:47
 */
public interface ExcelSuccessHandler<T> {

    ExcelFailHandler success(List<T> list);
}
