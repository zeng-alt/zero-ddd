package com.zjj.main.interfaces.mvc.rule.from;

import lombok.Data;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月16日 09:32
 */
@Data
public class PolicyRuleFrom {
    private Long id;
    private Boolean preAuth;
    private String condition;
    private Boolean enable;
    private Long permissionId;
}
