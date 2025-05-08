package com.zjj.graphql.component.query.condition;

import lombok.Data;

import java.util.Collection;
import java.util.List;

/**
 * @author zengJiaJun
 * @crateTime 2024年10月20日 21:37
 * @version 1.0
 */
@Data
public class Condition {
    private Collection<Object> value;
    private Option option;
}
