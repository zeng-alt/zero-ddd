package com.zjj.core.component.api;

import lombok.Data;

import java.util.Collection;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年02月25日 21:41
 */
@Data
public class TurnPageEntity<T extends Collection<?>, C> {

    private Boolean hasNext;
    private Boolean hasPre;
    private C currentCursor;
    private C nextCursor;
    private T data;

}
