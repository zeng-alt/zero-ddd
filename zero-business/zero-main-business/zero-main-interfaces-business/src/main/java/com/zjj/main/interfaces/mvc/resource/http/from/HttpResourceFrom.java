package com.zjj.main.interfaces.mvc.resource.http.from;

import com.zjj.main.domain.resource.http.cmd.StockInHttpResourceCmd;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotEmpty;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月17日 17:16
 */
@AutoMapper(target = StockInHttpResourceCmd.class)
public record HttpResourceFrom(
    Long id,
    String resourceType,
    @NotEmpty
    String code,
    @NotEmpty
    String name,
    @NotEmpty
    String path,
    String redirect,
    @NotEmpty
    String method,
    Boolean enable,
    Long menuId
) {
}
