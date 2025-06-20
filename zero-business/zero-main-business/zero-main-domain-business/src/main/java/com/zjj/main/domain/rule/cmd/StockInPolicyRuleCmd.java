package com.zjj.main.domain.rule.cmd;

import lombok.Data;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月16日 09:56
 */
@Data
public class StockInPolicyRuleCmd {
    private Long id;
    private Boolean preAuth;
    private String condition;
    private Boolean enable;
    private Long permissionId;
}
