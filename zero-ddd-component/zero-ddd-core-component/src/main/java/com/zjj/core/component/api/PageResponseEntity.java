package com.zjj.core.component.api;


import com.zjj.autoconfigure.component.core.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.Collections;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年02月20日 14:58
 */
public class PageResponseEntity<T> extends ResponseEntity<PageEntity<Collection<T>>> {

    public PageResponseEntity(HttpStatusCode status) {
        super(status);
    }

    public PageResponseEntity(@Nullable PageEntity<Collection<T>> body, HttpStatusCode status) {
        super(body, status);
    }

    public static PageResponseEntity<Void> of(int pageSize, int pageNum) {
        PageEntity<Collection<Void>> page = new PageEntity<>();
        page.setPageSize(pageSize);
        page.setPageNum(pageNum);
        page.setData(Collections.emptyList());
        return new PageResponseEntity<>(page, HttpStatus.OK);
    }

    public static <T> PageResponseEntity<T> of(Collection<T> data, long totalCount, int pageSize, int pageNum) {
        PageEntity<Collection<T>> page = new PageEntity<>();
        page.setData(data);
        page.setTotal(totalCount);
        page.setPageSize(pageSize);
        page.setPageNum(pageNum);
        return new PageResponseEntity<>(page, HttpStatus.OK);
    }
}
