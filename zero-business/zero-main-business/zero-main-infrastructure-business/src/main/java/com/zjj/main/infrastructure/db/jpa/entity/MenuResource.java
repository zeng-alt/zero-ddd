package com.zjj.main.infrastructure.db.jpa.entity;

import com.zjj.core.component.api.Parent;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月15日 21:42
 */
@Getter
@Setter
@Entity
@Table(name = "main_menu_resource")
@DiscriminatorValue("HTTP")
public class MenuResource extends Resource implements Parent<Long> {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    /**
     * 父菜单
     */
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private MenuResource parentMenu;

    /**
     * 子菜单
     */
    @OneToMany(mappedBy = "parentMenu")
    private Set<MenuResource> chileMenus = new LinkedHashSet<>();

    private String name;
    private String code;
    private String type;
    private String path;
    private String redirect;
    private String icon;
    private String component;
    private String layout;
    private String keepAlive;
    private String method;
    private String description;
    private Boolean show;
    private Boolean enable;
    @Column(name = "resource_order")
    private Integer order;



    @Override
    public Long parent() {
//        return parentMenu == null ? null : parentMenu.getId();
        return 0L;
    }

    @Override
    public Long current() {
        return getId();
    }

    @Override
    public String getKey() {
        return "http" + ":" + this.path + ":" + this.method;
    }
}
