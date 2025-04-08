package com.zjj.main.infrastructure.db.jpa.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "GraphqlResource")
@Table(name = "main_graphql_resource")
@DiscriminatorValue("GraphQL")
public class GraphqlResourceEntity extends Resource {
    private String operation;
}
