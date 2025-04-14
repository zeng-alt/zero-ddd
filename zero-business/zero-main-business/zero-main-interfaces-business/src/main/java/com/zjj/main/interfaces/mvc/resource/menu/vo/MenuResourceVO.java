package com.zjj.main.interfaces.mvc.resource.menu.vo;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年04月09日 16:38
 */
@Data
public class MenuResourceVO {

    private Long id;
    private Long parentId;
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
    private List<MenuResourceVO> children;
}
