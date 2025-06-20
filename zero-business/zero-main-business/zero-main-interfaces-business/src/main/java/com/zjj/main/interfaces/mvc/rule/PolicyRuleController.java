package com.zjj.main.interfaces.mvc.rule;

import com.zjj.domain.component.AbstractTxController;
import com.zjj.domain.component.command.CommandBus;
import com.zjj.main.domain.role.cmd.AuthorizePermissionCmd;
import com.zjj.main.domain.rule.cmd.StockInPolicyRuleCmd;
import com.zjj.main.interfaces.mvc.rule.from.PolicyRuleFrom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月16日 09:31
 */
@Tag(name = "安全规则管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/main/v1/policy/rule")
public class PolicyRuleController extends AbstractTxController {

    private final CommandBus commandBus;

    @Operation(summary = "保存或更新安全规则")
    @PostMapping
    public String saveRule(@RequestBody PolicyRuleFrom from) {
        this.beanConvert(from).to(StockInPolicyRuleCmd.class).accept(this.commandBus::emit);
        return "ok";
    }
}
