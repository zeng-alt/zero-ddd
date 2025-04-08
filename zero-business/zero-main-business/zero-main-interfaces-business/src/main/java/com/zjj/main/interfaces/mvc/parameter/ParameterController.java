package com.zjj.main.interfaces.mvc.parameter;

import com.zjj.domain.component.command.CommandBus;
import com.zjj.main.domain.parameter.cmd.InitUserPasswordCmd;
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
 * @crateTime 2025年03月28日 14:15
 */
@Tag(name = "参数管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/main/v1/parameter")
public class ParameterController {

    private final CommandBus commandBus;

    @Operation(summary = "初始化用户密码")
    @PostMapping("/init/password")
    public String initUserPassword(@RequestBody Long userId) {
        commandBus.emit(new InitUserPasswordCmd(userId));
        return "ok";
    }
}
