package com.zjj.tenant.domain.menu.policy;

import com.zjj.tenant.domain.menu.event.DisableMenuEvent;
import com.zjj.tenant.domain.menu.event.EnableMenuEvent;
import com.zjj.tenant.domain.menu.event.RemoveMenuEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月05日 21:26
 */
@Component
public class MenuResourcePolicy {

    @EventListener(value = DisableMenuEvent.class)
    public void handler(DisableMenuEvent event) {

    }

    @EventListener(value = EnableMenuEvent.class)
    public void handler(EnableMenuEvent event) {

    }


    @EventListener(value = RemoveMenuEvent.class)
    public void handler(RemoveMenuEvent event) {
        System.out.println(event);
    }
}
