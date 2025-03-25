package com.zjj.main.interfaces.mvc.user;

import com.zjj.domain.component.AbstractTxController;
import com.zjj.domain.component.command.CommandBus;
import com.zjj.main.domain.user.cmd.StockInUserCmd;
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
@Tag(name = "用户管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/main/v1/user")
public class UserController extends AbstractTxController {

//    private final StockInUserCmdHandler stockInUserCmdHandler;
    private final CommandBus commandBus;
    @PutMapping
    @Operation(summary = "保存或更新用户")
    public String saveUser(@RequestBody StockInUserFrom userFrom) {
//        this.execute(() ->
//                        convert(userFrom)
//                        .to(StockInUserCmd.class)
//                        .accept(stockInUserCmdHandler::handler)
//        );
//

        this.convert(userFrom).to(StockInUserCmd.class).accept(this.commandBus::emit);
        return "ok";
    }

}
