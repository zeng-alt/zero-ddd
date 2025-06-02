package com.zjj.main.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
public class MenuResourceDTO {

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
    private String menuStyle;
    private Integer order;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<MenuResourceDTO> children;
}