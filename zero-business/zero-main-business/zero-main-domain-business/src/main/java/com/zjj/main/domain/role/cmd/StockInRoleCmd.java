package com.zjj.main.domain.role.cmd;

import org.jmolecules.architecture.cqrs.Command;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月24日 16:09
 */
@Command
public record StockInRoleCmd(
        Long id,
        String roleName,
        String roleKey,
        String roleSort,
        String status,
        Integer deleted
) {
}
