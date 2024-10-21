package com.zjj.graphql.component.context;

import graphql.language.Type;
import graphql.language.TypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月21日 20:59
 */
@Data
@EqualsAndHashCode(exclude = {"associationType"})
@ToString(exclude = {"associationType"})
public class EntityGraphqlAttribute implements GraphqlType {

    private String name;
    private String type;
    private EntityGraphqlType associationType;
    private Boolean association;
    private Boolean collection;
    private Boolean embedded;
    private String description;
    private final Type inputType;
    private final Type connectionType;
    private final Type conditionType;
    private final Type entityType;

    public EntityGraphqlAttribute(final String name, final String type, final EntityGraphqlType associationType, final Boolean association, final Boolean collection, final Boolean embedded, final String description) {
        this.name = name;
        this.type = type;
        this.associationType = associationType;
        this.association = association;
        this.collection = collection;
        this.embedded = embedded;
        this.description = description;
        if (association || embedded) {
            this.inputType = TypeName.newTypeName(getInputTypeName()).build();
            this.connectionType = TypeName.newTypeName(getConditionTypeName()).build();
            this.conditionType = TypeName.newTypeName(getConditionTypeName()).build();
            this.entityType = TypeName.newTypeName(getEntityTypeName()).build();
        } else {
            this.entityType = TypeName.newTypeName(type).build();
            this.connectionType = entityType;
            this.conditionType = entityType;
            this.inputType = entityType;
        }
    }

    public Type adaptType(Type type) {
        if (collection) {
            return wrapCollectionType(type, description);
        }
        return type;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static class Builder {
        private String name;
        private String type;
        private EntityGraphqlType associationType;
        private Boolean association;
        private Boolean collection;
        private Boolean embedded;
        private String description;

        Builder() {
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder type(final String type) {
            this.type = type;
            return this;
        }

        public Builder associationType(final EntityGraphqlType associationType) {
            this.associationType = associationType;
            return this;
        }

        public Builder association(final Boolean association) {
            this.association = association;
            return this;
        }

        public Builder collection(final Boolean collection) {
            this.collection = collection;
            return this;
        }

        public Builder embedded(final Boolean embedded) {
            this.embedded = embedded;
            return this;
        }

        public Builder description(final String description) {
            this.description = description;
            return this;
        }

        public EntityGraphqlAttribute build() {
            return new EntityGraphqlAttribute(this.name, this.type, this.associationType, this.association, this.collection, this.embedded, this.description);
        }

        public String toString() {
            return "EntityGraphqlAttribute.EntityGraphqlAttributeBuilder(name=" + this.name + ", type=" + this.type + ", associationType=" + this.associationType + ", association=" + this.association + ", collection=" + this.collection + ", embedded=" + this.embedded + ", description=" + this.description + ")";
        }
    }
}
