package com.zjj.main.interfaces.mvc.role.from;

import lombok.Data;

import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年05月22日 21:58
 */
@Data
public class FunctionPermissionFrom {
    private Set<Long> graphqlIds;
    private Set<Long> roleIds;
}
