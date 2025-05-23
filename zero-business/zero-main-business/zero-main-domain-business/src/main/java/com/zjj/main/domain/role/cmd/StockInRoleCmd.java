package com.zjj.main.domain.role.cmd;

import org.jmolecules.architecture.cqrs.Command;

import java.util.List;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月24日 16:09
 */
@Command
public record StockInRoleCmd(
        Long id,
        String name,
        String code,
        String roleSort,
        Boolean enable,
        Set<Long> permissionIds,
        Integer deleted
) {
}
