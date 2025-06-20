package com.zjj.main.domain.resource.http.cmd;

import org.jmolecules.architecture.cqrs.Command;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月17日 17:16
 */
@Command
public record StockInHttpResourceCmd(
    Long id,
    String resourceType,

    String code,

    String name,
    String path,
    String redirect,
    String method,
    Boolean enable,
    Long menuId
) {
}
