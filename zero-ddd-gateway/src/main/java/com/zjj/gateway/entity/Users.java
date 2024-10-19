package com.zjj.gateway.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月17日 20:06
 */
@Getter
@Setter
@Entity
@Table(name="users")
public class Users {

    @Id
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "countries_id")
    private Countries countries;
}
