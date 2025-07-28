package com.zjj.main.infrastructure.db.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月15日 21:42
 */
@Getter
@Setter
@Entity
@Table(name = "main_menu_resource")
@DiscriminatorValue("MENU")
public class MenuResource extends Permission {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    /**
     * 父菜单
     */
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "parent_id")
    private MenuResource parentMenu;

    /**
     * 子菜单
     */
    @OneToMany(mappedBy = "parentMenu", cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private List<MenuResource> children = new LinkedList<>();

//    private String name;
//    private String code;
    private String type;
    private String path;
    private String redirect;
    private String icon;
    private String component;
    private String layout;
    private String keepAlive;
    private String description;
    private Boolean show;
    private Boolean enable;
    private String menuStyle;
    @Column(name = "resource_order")
    private Integer order;

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public String getKey() {
        return null;
    }

    //    @OneToMany(mappedBy = "menuResource")
//    private Set<RoleMenu> roleMenus = new LinkedHashSet<>();

//    @Override
//    public Long parent() {
////        return parentMenu == null ? null : parentMenu.getId();
//        return 0L;
//    }

//    @Override
//    public Long current() {
//        return getId();
//    }

}
