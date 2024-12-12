package com.zjj.autoconfigure.component.security.rbac;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月05日 16:51
 */
@Getter
@Setter
public class GraphqlResource extends AbstractResource {
    private String type;
    private String functionName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GraphqlResource that = (GraphqlResource) o;
        return Objects.equals(type, that.type) && Objects.equals(functionName, that.functionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), type, functionName);
    }
}
