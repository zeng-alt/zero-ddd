package com.zjj.core.component.api;

import lombok.Data;
import org.springframework.http.ProblemDetail;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年02月20日 15:01
 */
@Data
public class PageEntity<T extends Collection<?>> {

    private Integer pageNum = 1;
    private Integer pageSize = 0;
    private Long total = 0L;
    private T data = (T) new ArrayList<>();


    public PageEntity() {
    }

    public PageEntity(T data) {
        this.data = data;
        this.total = (long) data.size();
    }
}
