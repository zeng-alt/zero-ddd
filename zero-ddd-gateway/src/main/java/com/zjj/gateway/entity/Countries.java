package com.zjj.gateway.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月17日 10:41
 */
@Getter
@Setter
@Entity
@Table(name="countries")
public class Countries {

    @Id
    private Long id;

    private String name;

    private Integer age;

    private LocalDateTime birthday;

    @OneToMany(mappedBy = "countries", orphanRemoval = true)
    private Set<Users> userses = new LinkedHashSet<>();

}
