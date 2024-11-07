package com.zjj.tenant.domain.menu;


import com.zjj.bean.componenet.BeanHelper;
import com.zjj.tenant.domain.menu.cmd.StockInMenuResourceCmd;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月05日 20:41
 */
@Component
@RequiredArgsConstructor
public class MenuResourceFactory {

    private final MenuResourceRepository menuResourceRepository;

    public Consumer<Consumer<IMenuResource>> create(StockInMenuResourceCmd cmd) {
        if (cmd.parentId() == null) {
            return iMenuResourceConsumer -> iMenuResourceConsumer.accept(BeanHelper.copyToObject(cmd, MenuResource.class));
        }

        return menuResourceRepository
                .findById(cmd.parentId())
                .map(BeanHelper.copyToObject(cmd, MenuResource.class)::setParentMenu)
                .map(m -> (Consumer<Consumer<IMenuResource>>) iMenuResourceConsumer -> iMenuResourceConsumer.accept(m))
                .getOrElseThrow(() -> new IllegalArgumentException(cmd.parentId() + " 菜单不存在"));
    }

}
