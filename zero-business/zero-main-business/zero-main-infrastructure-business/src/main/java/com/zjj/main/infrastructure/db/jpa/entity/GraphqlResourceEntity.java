package com.zjj.main.infrastructure.db.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
@Setter
@Entity(name = "GraphqlResource")
@Table(name = "main_graphql_resource")
@DiscriminatorValue("GRAPHQL")
public class GraphqlResourceEntity extends Permission {

    @Column
    private String operation;
    @Column
    private String functionName;
    @Column
    private String uri;


    @Override
    public boolean isEmpty() {
        return !StringUtils.hasText(operation) || !StringUtils.hasText(functionName) || !StringUtils.hasText(uri);
    }

    @Override
    @Transient
    public String getKey() {
        return "graphql:" + uri + ":" + operation + ":" + functionName;
    }
}
