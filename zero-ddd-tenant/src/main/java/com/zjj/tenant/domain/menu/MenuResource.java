package com.zjj.tenant.domain.menu;

import com.zjj.autoconfigure.component.core.BaseException;
import com.zjj.core.component.api.Parent;
import com.zjj.domain.component.BaseAggregate;
import com.zjj.tenant.domain.menu.event.DisableMenuEvent;
import com.zjj.tenant.domain.menu.event.EnableMenuEvent;
import com.zjj.tenant.domain.menu.event.RemoveMenuEvent;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.TenantId;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

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
public class MenuResource extends BaseAggregate<Long> implements IMenuResource, Parent<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @TenantId
//    @Nullable
//    private String tenantBy;


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
    @ManyToOne(optional = true, cascade = {CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private MenuResource parentMenu;

    /**
     * 子菜单
     */
    @OneToMany(mappedBy = "parentMenu")
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

    @Override
    public IMenuResource remove() {
        if (!CollectionUtils.isEmpty(chileMenus)) {
            throw new BaseException("MenuResource.remove");
        }
        publishEvent(RemoveMenuEvent.apply(this, this.id));
        return this;
    }

    @Override
    public Long parent() {
        return parentMenu == null ? null : parentMenu.getId();
    }

    @Override
    public Long current() {
        return id;
    }
}
