package com.zjj.main.domain.user.cmd;

import org.jmolecules.architecture.cqrs.Command;

import java.util.List;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年05月21日 20:26
 */
@Command
public record CancelAssignRoleCmd(
        Long roleId,
        Set<Long> userIds
) {
}
