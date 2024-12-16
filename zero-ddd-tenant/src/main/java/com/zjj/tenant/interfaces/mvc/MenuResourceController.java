package com.zjj.tenant.interfaces.mvc;

import com.zjj.domain.component.AbstractTxController;
import com.zjj.security.abac.component.annotation.AbacPreAuthorize;
import com.zjj.tenant.domain.menu.MenuResourceHandler;
import com.zjj.tenant.domain.menu.cmd.DisableMenuCmd;
import com.zjj.tenant.domain.menu.cmd.EnableMenuCmd;
import com.zjj.tenant.domain.menu.cmd.RemoveMenuCmd;
import com.zjj.tenant.domain.menu.cmd.StockInMenuResourceCmd;
import com.zjj.tenant.interfaces.mvc.form.StockInMenuResourceForm;
import io.github.linpeilie.Converter;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月01日 21:27
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/tenant/v1/menu/resource")
public class MenuResourceController extends AbstractTxController {

    private final MenuResourceHandler menuResourceHandler;
    private final Converter converter;

    @Operation(summary = "新增或更新菜单资源")
    @PutMapping
    public String createMenuResource(@RequestBody StockInMenuResourceForm stockInTenantForm) {
        this.execute(() -> menuResourceHandler.handler(converter.convert(stockInTenantForm, StockInMenuResourceCmd.class)));
        return "ok";
    }

    @PostMapping("/disable/{id}")
    public String disableMenuResource(@PathVariable("id") Long id) {
        this.execute(() -> menuResourceHandler.handler(new DisableMenuCmd(id)));
        return "ok";
    }

    @PostMapping("/enable/{id}")
    public String enableMenuResource(@PathVariable("id") Long id) {
        this.execute(() -> menuResourceHandler.handler(new EnableMenuCmd(id)));
        return "ok";
    }

    @DeleteMapping("/{id}")
    @AbacPreAuthorize("delete:menu:resource")
    public String removeMenu(@PathVariable("id") Long id) {
        this.execute(() -> menuResourceHandler.handler(new RemoveMenuCmd(id)));
        return "ok";
    }
}
