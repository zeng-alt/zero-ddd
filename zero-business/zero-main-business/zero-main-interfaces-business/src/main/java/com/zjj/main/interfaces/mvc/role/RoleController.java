package com.zjj.main.interfaces.mvc.role;

import com.zjj.domain.component.AbstractTxController;
import com.zjj.domain.component.command.CommandBus;
import com.zjj.main.domain.role.cmd.*;
import com.zjj.main.interfaces.mvc.role.from.FunctionPermissionFrom;
import com.zjj.main.interfaces.mvc.role.from.ServicePermissionFrom;
import com.zjj.main.interfaces.mvc.role.from.StockInRoleFrom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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


    private final CommandBus commandBus;
    @PostMapping
    @Operation(summary = "保存或更新角色")
    public String saveUser(@RequestBody StockInRoleFrom roleFrom) {
        this.convert(roleFrom).to(StockInRoleCmd.class).accept(this.commandBus::emit);
        return "ok";
    }


    @PostMapping("/service/authorize")
    public String serviceAuthorize(@RequestBody ServicePermissionFrom from) {
        this.beanConvert(from).to(ServiceAuthorizeCmd.class).accept(this.commandBus::emit);
        return "ok";
    }

    @PostMapping("/function/authorize")
    public String functionAuthorize(@RequestBody FunctionPermissionFrom from) {
        this.beanConvert(from).to(FunctionAuthorizeCmd.class).accept(this.commandBus::emit);
        return "ok";
    }

    @PostMapping("/service/cancel/authorize")
    public String serviceCancelAuthorize(@RequestBody ServicePermissionFrom from) {
        this.beanConvert(from).to(ServiceCancelAuthorizeCmd.class).accept(this.commandBus::emit);
        return "ok";
    }

    @PostMapping("/function/cancel/authorize")
    public String functionCancelAuthorize(@RequestBody FunctionPermissionFrom from) {
        this.beanConvert(from).to(FunctionCancelAuthorizeCmd.class).accept(this.commandBus::emit);
        return "ok";
    }
}
