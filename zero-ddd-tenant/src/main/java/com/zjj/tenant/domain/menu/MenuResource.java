package com.zjj.tenant.domain.menu;

import com.zjj.domain.component.BaseAggregate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月30日 15:50
 */
@Entity
@Table(name = "menu_resource")
@Getter
@Setter
public class MenuResource extends BaseAggregate<Long> {

    @Id
    @GeneratedValue
    private Long id;
}
