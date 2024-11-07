package com.zjj.tenant.domain.menu;

import com.zjj.domain.component.BaseAggregate;
import com.zjj.tenant.domain.menu.event.DisableMenuEvent;
import com.zjj.tenant.domain.menu.event.EnableMenuEvent;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月30日 15:50
 */
@Entity
@Table(name = "menu_resource")
@Getter
@Setter
public class MenuResource extends BaseAggregate<Long> implements IMenuResource {

    @Id
    @GeneratedValue
    private Long id;


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
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private MenuResource parentMenu;

    /**
     * 子菜单
     */
    @OneToMany(mappedBy = "parentMenu", orphanRemoval = true)
    private Set<MenuResource> chileMenus = new LinkedHashSet<>();

    @Override
    public IMenuResource disable() {
        this.status = "1";
        publishEvent(DisableMenuEvent.apply(this, this.id));
        return this;
    }

    @Override
    public IMenuResource enable() {
        this.status = "0";
        publishEvent(EnableMenuEvent.apply(this, this.id));
        return this;
    }

    @Override
    public IMenuResource setParentMenu(IMenuResource parentMenu) {
        if (parentMenu instanceof MenuResource menuResource) {
            this.parentMenu = menuResource;
            return this;
        }
        throw new IllegalArgumentException("parentMenu 不是 MenuResource 类型");
    }
}
