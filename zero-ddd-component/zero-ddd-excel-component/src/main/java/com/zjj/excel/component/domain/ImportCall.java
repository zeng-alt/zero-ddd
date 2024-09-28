package com.zjj.excel.component.domain;



import java.util.List;
import java.util.function.Consumer;


/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年07月05日 14:53
 */
@FunctionalInterface
public interface ImportCall<T> {

    String call(Consumer<List<T>> success, Consumer<List<String>> error);
}
