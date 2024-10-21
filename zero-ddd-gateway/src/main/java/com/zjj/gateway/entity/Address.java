package com.zjj.gateway.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Embeddable
public class Address {
    private String address;
    private Integer code;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users;

    @Embedded
    private Abc abc;
}