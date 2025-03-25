package com.zjj.main.interfaces.mvc.role.from;


import com.zjj.main.domain.role.cmd.StockInRoleCmd;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月21日 21:37
 */
@Data
@AutoMapper(target = StockInRoleCmd.class)
public class StockInRoleFrom implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色权限
     */
    private String roleKey;

    /**
     * 角色排序
     */
    private Integer roleSort;


    /**
     * 角色状态（0正常 1停用）
     */
    private String status = "0";

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private Integer deleted = 0;
}
