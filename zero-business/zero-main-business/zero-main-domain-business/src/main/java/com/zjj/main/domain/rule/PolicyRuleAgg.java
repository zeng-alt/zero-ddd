package com.zjj.main.domain.rule;

import com.zjj.domain.component.Aggregate;
import com.zjj.main.domain.permission.PermissionAgg;
import com.zjj.main.domain.permission.PermissionId;
import lombok.Data;
import org.jmolecules.ddd.types.Association;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月16日 09:38
 */
@Data
public class PolicyRuleAgg extends Aggregate<PolicyRuleAgg, PolicyRuleId> implements PolicyRule {

    private PolicyRuleId id;
    private Boolean preAuth;
    private String condition;
    private Boolean enable;
    private Association<PermissionAgg, PermissionId> permission;


}
