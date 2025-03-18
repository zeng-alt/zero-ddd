package com.zjj.tenant.domain.menu;

import com.zjj.autoconfigure.component.core.BaseException;
import com.zjj.bean.componenet.ApplicationContextHelper;
import com.zjj.core.component.api.Parent;
import com.zjj.tenant.domain.menu.event.DisableMenuEvent;
import com.zjj.tenant.domain.menu.event.EnableMenuEvent;
import com.zjj.tenant.domain.menu.event.RemoveMenuEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.jmolecules.ddd.types.Association;
import org.jmolecules.ddd.types.Identifier;
import org.springframework.util.CollectionUtils;

import java.util.LinkedHashSet;
import java.util.Set;



/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月30日 15:50
 */
@Getter
@Setter
public class MenuResource implements MenuResourceAggregate, Parent<Long> {


    private MenuResourceId id;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 路由参数
     */
    private String queryParam;

    /**
     * 是否为外链（0是 1否）
     */
    private String isFrame;

    /**
     * 是否缓存（0缓存 1不缓存）
     */
    private String isCache;

    /**
     * 类型（M目录 C菜单 F按钮）
     */
    private String menuType;

    /**
     * 显示状态（0显示 1隐藏）
     */
    private String visible;

    /**
     * 菜单状态（0正常 1停用）
     */
    private String status;

    /**
     * 菜单图标
     */
    private String icon;


    /**
     * 父菜单
     */
    private Association<MenuResource, MenuResourceId> parentMenu;


    private Set<Association<MenuResource, MenuResourceId>> chileMenus = new LinkedHashSet<>();

    @Value(staticConstructor = "of")
    public static class MenuResourceId implements Identifier {
        Long id;
    }

    @Override
    public MenuResourceAggregate disable() {
        this.status = "1";
        ApplicationContextHelper.publisher().publishEvent(DisableMenuEvent.apply(this, this.id.getId()));
        return this;
    }

    @Override
    public MenuResourceAggregate enable() {
        this.status = "0";
        ApplicationContextHelper.publisher().publishEvent(EnableMenuEvent.apply(this, this.id.getId()));
        return this;
    }

    @Override
    public MenuResourceAggregate setParentMenu(MenuResourceAggregate parentMenu) {
        if (parentMenu instanceof MenuResource menuResource) {
            this.parentMenu = Association.forAggregate(menuResource);
//            parentMenu
            return this;
        }
        throw new IllegalArgumentException("parentMenu 不是 MenuResource 类型");
    }

    @Override
    public MenuResourceAggregate remove() {
        if (!CollectionUtils.isEmpty(chileMenus)) {
            throw new BaseException("MenuResource.remove");
        }
        ApplicationContextHelper.publisher().publishEvent(RemoveMenuEvent.apply(this, this.id.getId()));
        return this;
    }

    @Override
    public Long parent() {
        return parentMenu == null ? null : parentMenu.getId().getId();
    }

    @Override
    public Long current() {
        return id.getId();
    }
}
