package com.zjj.main.interfaces.mvc.role;

import com.zjj.domain.component.AbstractTxController;
import com.zjj.main.domain.role.StockInRoleCmd;
import com.zjj.main.domain.role.StockInRoleCmdHandler;
import com.zjj.main.domain.user.StockInUserCmd;
import com.zjj.main.domain.user.StockInUserCmdHandler;
import com.zjj.main.interfaces.mvc.role.from.StockInRoleFrom;
import com.zjj.main.interfaces.mvc.user.from.StockInUserFrom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月19日 21:47
 */
@Tag(name = "角色管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/main/v1/role")
public class RoleController extends AbstractTxController {

    private final StockInRoleCmdHandler stockInRoleCmdHandler;
    @PutMapping
    @Operation(summary = "保存或更新角色")
    public String saveUser(@RequestBody StockInRoleFrom roleFrom) {
        this.execute(() ->
                        convert(roleFrom)
                        .to(StockInRoleCmd.class)
                        .accept(stockInRoleCmdHandler::handler)
        );
        return "ok";
    }
}
