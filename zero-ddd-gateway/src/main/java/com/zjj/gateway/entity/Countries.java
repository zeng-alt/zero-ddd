package com.zjj.gateway.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月17日 20:41
 */
//@IgnoreEntity
@Getter
@Setter
@Entity
@Table(name="countries")
public class Countries {

    @Id
    @Comment(
            value = "国家ID")
    private Long id;

    private String name;

    private Integer age;

    private LocalDate birthday;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "countries", orphanRemoval = true)
    private Set<Users> userses = new LinkedHashSet<>();

    private transient Set<String> friends = new LinkedHashSet<>();

}
