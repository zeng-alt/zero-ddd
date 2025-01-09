package com.zjj.tenant.domain.menu;

import com.zjj.i18n.component.BaseI18nException;
import com.zjj.tenant.domain.menu.cmd.DisableMenuCmd;
import com.zjj.tenant.domain.menu.cmd.EnableMenuCmd;
import com.zjj.tenant.domain.menu.cmd.RemoveMenuCmd;
import com.zjj.tenant.domain.menu.cmd.StockInMenuResourceCmd;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月05日 20:07
 */
@Service
@RequiredArgsConstructor
public class MenuResourceHandler {

    private final MenuResourceRepository menuResourceRepository;
    private final MenuResourceFactory menuResourceFactory;

    public void handler(StockInMenuResourceCmd cmd) {
        menuResourceFactory
                .create(cmd)
                .accept(menuResourceRepository::save);
    }

    public void handler(DisableMenuCmd disableMenuCmd) {
        menuResourceRepository
                .findById(disableMenuCmd.id())
                .map(MenuResourceAggregate::disable)
                .map(menuResourceRepository::save)
                .getOrElseThrow(() -> new BaseI18nException(MenuResponseEnum.MENU_NOT_EXIST, disableMenuCmd.id()));
    }

    public void handler(EnableMenuCmd enableMenuCmd) {
        menuResourceRepository
                .findById(enableMenuCmd.id())
                .map(MenuResourceAggregate::enable)
                .map(menuResourceRepository::save)
                .getOrElseThrow(() -> new BaseI18nException(MenuResponseEnum.MENU_NOT_EXIST, enableMenuCmd.id()));
    }

    public void handler(RemoveMenuCmd removeMenuCmd) {
        menuResourceRepository
                .findById(removeMenuCmd.id())
                .map(MenuResourceAggregate::remove)
                .peek(menuResourceRepository::remove)
                .getOrElseThrow(() -> new BaseI18nException(MenuResponseEnum.MENU_NOT_EXIST, removeMenuCmd.id()));
    }
}
