package com.zjj.tenant.domain.menu.cmd;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月05日 20:33
 */
public record StockInMenuResourceCmd(
        Long parentId,
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
