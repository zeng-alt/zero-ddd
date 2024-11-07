package com.zjj.tenant.interfaces.mvc;

import com.zjj.domain.component.AbstractTxController;
import com.zjj.tenant.domain.tenant.TenantHandler;
import com.zjj.tenant.domain.tenant.cmd.DisableTenantCmd;
import com.zjj.tenant.domain.tenant.cmd.EnableTenantCmd;
import com.zjj.tenant.interfaces.mvc.form.StockInTenantForm;
import com.zjj.tenant.interfaces.mvc.transform.StockInTenantFormTransform;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月01日 21:27
 */
@Tag(name = "租户管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/tenant")
public class TenantController extends AbstractTxController {

    private final TenantHandler tenantHandler;
    private final StockInTenantFormTransform stockInTenantFormTransform;

    @Operation(summary = "创建租户")
    @PostMapping
    public String createTenant(@RequestBody StockInTenantForm stockInTenantForm) {

        this.execute(() ->
                tenantHandler.handler(stockInTenantFormTransform.transform(stockInTenantForm))
        );

        return "ok";
    }


    @Operation(summary = "禁用租户")
    @PostMapping("/disable/{id}")
    public String disableMenuResource(@PathVariable("id") Long id) {
        this.execute(() -> tenantHandler.handler(new DisableTenantCmd(id)));
        return "ok";
    }


    @Operation(summary = "启用租户")
    @PostMapping("/enable/{id}")
    public String enableMenuResource(@PathVariable("id") Long id) {
        this.execute(() -> tenantHandler.handler(new EnableTenantCmd(id)));
        return "ok";
    }
}
