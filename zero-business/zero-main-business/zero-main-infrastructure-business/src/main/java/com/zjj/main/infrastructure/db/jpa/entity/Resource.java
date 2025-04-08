package com.zjj.main.infrastructure.db.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zjj.domain.component.BaseEntity;
import com.zjj.domain.component.TenantAuditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.TenantId;
import org.springframework.lang.Nullable;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Entity
@Setter
@Table(name = "main_resource")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "resource_type", discriminatorType = DiscriminatorType.STRING)
public class Resource extends BaseEntity<Long> implements TenantAuditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "resource_type", insertable = false, updatable = false)
    private String resourceType;

    @OneToMany(mappedBy = "resource")
    private Set<Expression> expressions = new LinkedHashSet<>();

//    @OneToOne(mappedBy = "resource")
//    private Permission permission;

    @TenantId
    @Nullable
    private String tenantBy;

    @JsonIgnore
    public String getKey() {
        throw new UnsupportedOperationException();
    }
}
