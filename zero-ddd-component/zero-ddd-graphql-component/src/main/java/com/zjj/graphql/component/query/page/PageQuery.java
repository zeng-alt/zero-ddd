package com.zjj.graphql.component.query.page;

import graphql.PublicApi;
import lombok.Data;

/**
 * @author zengJiaJun
 * @crateTime 2024年10月19日 20:56
 * @version 1.0
 */
@PublicApi
@Data
public class PageQuery {
    private Integer first = 10;
    private Integer last = 10;
    private String after;
    private String before;
}
