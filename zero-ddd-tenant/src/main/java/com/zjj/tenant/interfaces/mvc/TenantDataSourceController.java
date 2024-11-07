package com.zjj.tenant.interfaces.mvc;


import com.zjj.domain.component.AbstractTxController;
import com.zjj.tenant.domain.tenant.TenantHandler;
import com.zjj.tenant.interfaces.mvc.form.TenantDataSourceForm;
import com.zjj.tenant.interfaces.mvc.transform.TenantDataSourceFormTransform;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private final TenantDataSourceFormTransform tenantDataSourceFormTransform;

    @PutMapping
    public String updateTenantDataSource(@RequestBody TenantDataSourceForm tenantDataSourceForm) {
        this.execute(() -> tenantHandler.handler(tenantDataSourceFormTransform.transform(tenantDataSourceForm)));
        return "ok";
    }
}
