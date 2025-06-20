package com.zjj.main.interfaces.mvc.resource.http;

import com.zjj.domain.component.AbstractTxController;
import com.zjj.domain.component.command.CommandBus;
import com.zjj.main.domain.resource.http.cmd.DeleteHttpResourceCmd;
import com.zjj.main.domain.resource.http.cmd.StockInHttpResourceCmd;
import com.zjj.main.interfaces.mvc.resource.http.from.HttpResourceFrom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年06月17日 17:34
 */
@Tag(name = "http资源管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/main/v1/http/resource")
public class HttpResourceController extends AbstractTxController {

    private final CommandBus commandBus;

    @PostMapping
    @Operation(summary = "保存或更新http资源")
    public String saveHttpResource(@RequestBody HttpResourceFrom from) {
        this.convert(from)
                .to(StockInHttpResourceCmd.class)
                .accept(this.commandBus::emit);
        return "ok";
    }

    @Operation(summary = "根据id删除http资源")
    @DeleteMapping("/{id}")
    public String deleteHttpResource(@PathVariable Long id) {
        this.commandBus.emit(new DeleteHttpResourceCmd(id));
        return "ok";
    }
}
