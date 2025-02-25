package com.zjj.core.component.api;

import lombok.Data;

import java.util.Collection;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年02月20日 15:01
 */
@Data
public class PageEntity<T extends Collection<?>> {

    private Integer pageNum;
    private Integer pageSize;
    private Long total;
    private T data;
}
