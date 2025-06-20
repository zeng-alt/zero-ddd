package com.zjj.tenant.domain.tenant.cmd;

import org.jmolecules.architecture.cqrs.Command;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月03日 17:31
 */
@Command
public record TestDataSourceConnectionCmd(Long id) {
}
