package com.zjj.tenant.interfaces.mvc;

import com.zjj.domain.component.AbstractTxController;
import com.zjj.domain.component.TransactionCallbackWithoutResult;
import com.zjj.tenant.domain.menu.MenuResourceHandler;
import com.zjj.tenant.domain.menu.cmd.DisableMenuCmd;
import com.zjj.tenant.domain.menu.cmd.EnableMenuCmd;
import com.zjj.tenant.domain.menu.cmd.StockInMenuResourceCmd;
import com.zjj.tenant.interfaces.mvc.form.StockInMenuResourceForm;
import io.github.linpeilie.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月01日 21:27
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/menu/resource")
public class MenuResourceController extends AbstractTxController {

    private final MenuResourceHandler menuResourceHandler;
    private final Converter converter;

    @PostMapping
    public String createMenuResource(@RequestBody StockInMenuResourceForm stockInTenantForm) {
        this.execute(() -> menuResourceHandler.handler(converter.convert(stockInTenantForm, StockInMenuResourceCmd.class)));
//        return "ok";
        throw new RuntimeException("test1");
    }


    @PostMapping("/disable/{id}")
    public String disableMenuResource(@PathVariable("id") Long id) {
        transactionTemplate.execute((TransactionCallbackWithoutResult) () ->
                menuResourceHandler.handler(new DisableMenuCmd(id))
        );
        return "ok";
    }

    @PostMapping("/enable/{id}")
    public String enableMenuResource(@PathVariable("id") Long id) {

        transactionTemplate.execute((TransactionCallbackWithoutResult) () ->
                menuResourceHandler.handler(new EnableMenuCmd(id))
        );

        return "ok";
    }
}
