package com.zjj.tenant.interfaces.mvc;


import com.zjj.domain.component.AbstractTxController;
import com.zjj.domain.component.command.CommandBus;
import com.zjj.tenant.domain.tenant.TenantHandler;
import com.zjj.tenant.domain.tenant.cmd.TestDataSourceConnectionCmd;
import com.zjj.tenant.interfaces.mvc.form.TenantDataSourceForm;
import com.zjj.tenant.interfaces.mvc.transform.TenantDataSourceFormTransform;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月04日 21:25
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/tenant/source")
public class TenantDataSourceController extends AbstractTxController {

    private final TenantHandler tenantHandler;
    private final CommandBus bus;
    private final TenantDataSourceFormTransform tenantDataSourceFormTransform;

    @PutMapping
    public String updateTenantDataSource(@RequestBody TenantDataSourceForm tenantDataSourceForm) {
        this.execute(() -> tenantHandler.handler(tenantDataSourceFormTransform.transform(tenantDataSourceForm)));
        return "ok";
    }


    @Operation(summary = "测试数据源是否可连接")
    @GetMapping("/test/{id}")
    public String testDataSource(@PathVariable Long id) {
        bus.emit(new TestDataSourceConnectionCmd(id));
//        tenantHandler.handler(new TestDataSourceConnectionCmd(id));
        return "ok";
    }

}
