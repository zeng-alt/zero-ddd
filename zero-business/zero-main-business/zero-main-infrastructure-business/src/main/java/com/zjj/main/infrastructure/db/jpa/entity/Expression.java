package com.zjj.main.infrastructure.db.jpa.entity;

import com.zjj.domain.component.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年11月15日 21:43
 */
@Getter
@Setter
@Entity
@Table(name = "main_expression")
public class Expression extends BaseEntity<Long> {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "resource_id")
    private MenuResource resource;

}
