package com.zjj.excel.component.domain;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年07月23日 17:32
 */
public class ExcelHandlerManger<T> {

    public ExcelHandlerManger<T> invoke(InvokeFunction<TestHandler> invokeFunction) {
        return this;
    }

    public ExcelHandlerManger<T> success(Consumer<List<T>> consumer) {
        return this;
    }

    public ExcelHandlerManger<T> fail(Consumer<List<String>> consumer) {
        return this;
    }
}
