package com.zjj.main.infrastructure.db.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年05月25日 16:29
 */
@Getter
@Setter
@Entity
@Table(name = "main_http_resource")
@DiscriminatorValue("HTTP")
public class HttpResource extends Permission {

    @Column
    private String path;
    @Column
    private String redirect;
    @Column
    private String method;
    @Column
    private Boolean enable;
    @Column
    private Long menuId;

    @Override
    public boolean isEmpty() {
        return !StringUtils.hasText(this.path) || !StringUtils.hasText(this.method);
    }

    @Override
    @Transient
    public String getKey() {
        return "http" + ":" + this.path + ":" + this.method;
    }
}
