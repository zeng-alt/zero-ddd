package com.zjj.tenant.interfaces.mvc;

import com.zjj.domain.component.AbstractTxController;
import com.zjj.tenant.application.service.MenuApplication;
import com.zjj.tenant.domain.tenant.TenantHandler;
import com.zjj.tenant.domain.tenant.cmd.DisableTenantCmd;
import com.zjj.tenant.domain.tenant.cmd.DisableTenantMenuCmd;
import com.zjj.tenant.domain.tenant.cmd.EnableTenantCmd;
import com.zjj.tenant.domain.tenant.cmd.EnableTenantMenuCmd;
import com.zjj.tenant.interfaces.mvc.form.DisableTenantMenuForm;
import com.zjj.tenant.interfaces.mvc.form.EnableTenantMenuForm;
import com.zjj.tenant.interfaces.mvc.form.TenantMenuForm;
import io.github.linpeilie.Converter;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月04日 21:18
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/tenant/menu")
public class TenantMenuController extends AbstractTxController {

    private final MenuApplication menuApplication;
    private final TenantHandler tenantHandler;
    private final Converter converter;

    @PutMapping
    public String updateTenantMenu(@RequestBody TenantMenuForm tenantMenuForm) {
        this.execute(() -> menuApplication.updateTenantMenu(tenantMenuForm));
        return "ok";
    }


    @Operation(summary = "禁用租户菜单")
    @PostMapping("/disable")
    public String disableTenantMenu(@RequestBody DisableTenantMenuForm disableTenantMenuForm) {
        this.execute(() -> tenantHandler.handler(converter.convert(disableTenantMenuForm, DisableTenantMenuCmd.class)));
        return "ok";
    }


    @Operation(summary = "启用菜单")
    @PostMapping("/enable")
    public String enableTenantMenu(@RequestBody EnableTenantMenuForm enableTenantMenuForm) {
        this.execute(() -> tenantHandler.handler(converter.convert(enableTenantMenuForm, EnableTenantMenuCmd.class)));
        return "ok";
    }
}
