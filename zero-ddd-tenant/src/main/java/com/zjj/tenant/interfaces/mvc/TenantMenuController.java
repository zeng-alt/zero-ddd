package com.zjj.tenant.interfaces.mvc;

import com.zjj.domain.component.AbstractTxController;
import com.zjj.tenant.application.service.MenuApplication;
import com.zjj.tenant.interfaces.mvc.form.TenantMenuForm;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PutMapping
    public String updateTenantMenu(@RequestBody TenantMenuForm tenantMenuForm) {
        this.execute(() -> menuApplication.updateTenantMenu(tenantMenuForm));
        return "ok";
    }
}
