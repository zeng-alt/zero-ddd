package com.zjj.main.domain.resource.http.cmd;

import org.jmolecules.architecture.cqrs.Command;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月17日 17:17
 */
@Command
public record DeleteHttpResourceCmd(Long id) {
}
