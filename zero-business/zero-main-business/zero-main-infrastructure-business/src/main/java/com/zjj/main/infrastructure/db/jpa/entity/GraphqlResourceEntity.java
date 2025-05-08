package com.zjj.main.infrastructure.db.jpa.entity;

import jakarta.persistence.*;

@Entity(name = "GraphqlResource")
@Table(name = "main_graphql_resource")
@DiscriminatorValue("GRAPHQL")
public class GraphqlResourceEntity extends Resource {

    @Column
    private String operation;
    @Column
    private String functionName;
    @Column
    private String uri;

    public GraphqlResourceEntity() {}

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    @Transient
    public String getKey() {
        return "graphql:" + uri + ":" + operation + ":" + functionName;
    }
}
