package com.zjj.gateway.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月17日 17:06
 */
@Getter
@Setter
@Entity
@Table(name="countries")
public class Users {

    @Id
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "countries_id")
    private Countries countries;
}
