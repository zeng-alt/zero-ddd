package com.zjj.core.component.api;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月11日 21:42
 */
@Data
public abstract class TurnPageQuery<T extends Serializable> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer size;
    private T current;
}
