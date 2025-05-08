package com.zjj.autoconfigure.component.security.rbac;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月05日 16:51
 */
@Getter
@Setter
@ToString(callSuper = true)
public class GraphqlResource extends AbstractResource {

    private String operation;
    private String functionName;


    public GraphqlResource() {
        this.method = "POST";
    }

    @Override
    public String getKey() {
        return uri + ":" + operation + ":" + functionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GraphqlResource that = (GraphqlResource) o;
        return Objects.equals(operation, that.operation) && Objects.equals(functionName, that.functionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), operation, functionName);
    }
}
