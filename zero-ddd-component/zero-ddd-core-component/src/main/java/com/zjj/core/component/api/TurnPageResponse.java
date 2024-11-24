package com.zjj.core.component.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zjj.autoconfigure.component.core.Response;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月11日 21:43
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TurnPageResponse<T, C extends Serializable> extends Response<List<T>> {

    @Serial
    private static final long serialVersionUID = 1L;

    private Boolean hasNext;
    private Boolean hasPre;
    private C currentCursor;
    private C nextCursor;
}
