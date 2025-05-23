package com.zjj.main.domain.user.cmd;

import org.jmolecules.architecture.cqrs.Command;

import java.util.List;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月24日 16:29
 */
@Command
public record AssignRoleCmd(
        Long roleId,
        Set<Long> userIds
) {
}
