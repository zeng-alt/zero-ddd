package com.zjj.tenant.interfaces.mvc.form;


import com.zjj.tenant.domain.menu.cmd.StockInMenuResourceCmd;
import io.github.linpeilie.annotations.AutoMapper;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月05日 20:52
 */
@AutoMapper(target = StockInMenuResourceCmd.class)
public record StockInMenuResourceForm(
        String parentId,
        String menuName,

        Integer orderNum,

        String path,

        String component,

        String queryParam,

        String isFrame,

        String isCache,

        String menuType,

        String visible,

        String status,

        String icon
) {
}
