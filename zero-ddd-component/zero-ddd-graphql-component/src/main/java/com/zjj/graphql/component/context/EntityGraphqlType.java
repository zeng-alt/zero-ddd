package com.zjj.graphql.component.context;

import com.querydsl.core.util.BeanUtils;
import graphql.language.Type;
import graphql.language.TypeName;
import lombok.Data;

import java.util.Set;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月21日 20:58
 */
@Data
public class EntityGraphqlType implements GraphqlType {

    private String name;
    private String type;
    private final Type inputType;
    private final Type connectionType;
    private final Type conditionType;
    private final Type entityType;
    private Set<EntityGraphqlAttribute> attributes;

    EntityGraphqlType(final String type, final Set<EntityGraphqlAttribute> attributes) {
        this.type = type;
        this.name = BeanUtils.uncapitalize(type);
        this.attributes = attributes;
        entityType = TypeName.newTypeName(getType()).build();
        inputType = TypeName.newTypeName(getInputTypeName()).build();
        connectionType = TypeName.newTypeName(getConnectionTypeName()).build();
        conditionType = TypeName.newTypeName(getConditionTypeName()).build();
    }

    public static Builder builder() {
        return new Builder();
    }



    public static class Builder {
        private String type;
        private Set<EntityGraphqlAttribute> attributes;

        Builder() {
        }

        public Builder type(final String type) {
            this.type = type;
            return this;
        }

        public Builder attributes(final Set<EntityGraphqlAttribute> attributes) {
            this.attributes = attributes;
            return this;
        }

        public EntityGraphqlType build() {
            return new EntityGraphqlType(this.type, this.attributes);
        }

        public String toString() {
            return "EntityGraphqlType.EntityGraphqlTypeBuilder(name=" + this.type + ", attributes=" + this.attributes + ")";
        }
    }


}
