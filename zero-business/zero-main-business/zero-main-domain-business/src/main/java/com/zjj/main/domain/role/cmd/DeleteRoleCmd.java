package com.zjj.main.domain.role.cmd;

import org.jmolecules.architecture.cqrs.Command;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月17日 11:30
 */
@Command
public record DeleteRoleCmd(Long id) {
}
