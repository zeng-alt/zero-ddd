package com.zjj.tenant.domain.menu;

import com.zjj.bean.componenet.BeanHelper;
import com.zjj.tenant.domain.menu.cmd.DisableMenuCmd;
import com.zjj.tenant.domain.menu.cmd.EnableMenuCmd;
import com.zjj.tenant.domain.menu.cmd.StockInMenuResourceCmd;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

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
                .map(IMenuResource::disable)
                .map(menuResourceRepository::save)
                .getOrElseThrow(() -> new IllegalArgumentException(disableMenuCmd.id() + " 菜单不存在"));
    }

    public void handler(EnableMenuCmd enableMenuCmd) {
        menuResourceRepository
                .findById(enableMenuCmd.id())
                .map(IMenuResource::enable)
                .map(menuResourceRepository::save)
                .getOrElseThrow(() -> new IllegalArgumentException(enableMenuCmd.id() + " 菜单不存在"));
    }

}
