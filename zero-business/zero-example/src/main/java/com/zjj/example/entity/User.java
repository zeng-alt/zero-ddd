package com.zjj.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年02月20日 17:05
 */
@Data
@Entity
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "{name.notnull}")
    @Size(min = 2, max = 50, message = "name.size")
    private String name;

    @NotNull(message = "{age.notnull}")
    private Integer age;
}
