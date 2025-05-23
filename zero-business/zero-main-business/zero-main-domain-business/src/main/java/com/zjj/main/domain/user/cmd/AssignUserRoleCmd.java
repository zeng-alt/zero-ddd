package com.zjj.main.domain.user.cmd;

import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年05月23日 14:13
 */

public record AssignUserRoleCmd(
        Long userId,
        Set<Long> roleIds
) {
}
