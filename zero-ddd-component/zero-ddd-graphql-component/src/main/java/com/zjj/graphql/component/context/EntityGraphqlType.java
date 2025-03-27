package com.zjj.graphql.component.context;

import com.querydsl.core.util.BeanUtils;
import com.zjj.domain.component.BaseEntity;
import com.zjj.graphql.component.annotations.MutationEntity;
import com.zjj.graphql.component.spi.EntitySaveHandler;
import graphql.language.Type;
import graphql.language.TypeName;
import lombok.Data;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.OrderComparator;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月21日 20:58
 */
@Data
public class EntityGraphqlType<T> implements GraphqlType {

    private String name;
    private String type;
    private String comment;
    private boolean embedded;
    private String idName;
    private final Type idType;
    private final Type inputType;
    private final Type connectionType;
    private final Type conditionType;
    private final Type entityType;
    private Set<EntityGraphqlAttribute> attributes;

    // save相关
    private List<EntitySaveHandler<T>> saveHandlers = new ArrayList<>();
    private boolean enableSave = true;
    private boolean enableBatchSave = true;
    private boolean enableDelete = true;
    private boolean enableDeleteId = true;

    EntityGraphqlType(final String type, final Set<EntityGraphqlAttribute> attributes, boolean embedded, String idType, String idName) {
        this.idType = TypeName.newTypeName(idType).build();
        this.idName = idName;
        this.type = type;
        this.name = BeanUtils.uncapitalize(type);
        this.attributes = attributes;
        this.embedded = embedded;
        entityType = TypeName.newTypeName(getType()).build();
        inputType = TypeName.newTypeName(getInputTypeName()).build();
        connectionType = TypeName.newTypeName(getConnectionTypeName()).build();
        conditionType = TypeName.newTypeName(getConditionTypeName()).build();
    }

    public void initMutation(Class<T> tClass, List<EntitySaveHandler<T>> saveHandlers) {
        MutationEntity annotation = AnnotationUtils.findAnnotation(tClass, MutationEntity.class);
        if (annotation == null) {
            return;
        }
        Set<Class<? extends EntitySaveHandler<? extends BaseEntity>>> set = Arrays.stream(annotation.saveHandlers()).collect(Collectors.toSet());
        this.saveHandlers.addAll(
                saveHandlers
                    .stream()
                    .filter(entitySaveHandler -> set.contains(entitySaveHandler.getClass()))
                    .sorted(OrderComparator.INSTANCE)
                    .toList()
        );

        this.enableSave = annotation.enableSave();
        this.enableBatchSave = annotation.enableBatchSave();
        this.enableDelete = annotation.enableDelete();
        this.enableDeleteId = annotation.enableDeleteId();
    }

    public static Builder builder() {
        return new Builder();
    }



    public static class Builder {
        private String type;
        private boolean embedded;
        private String idType;
        private String idName;
        private Set<EntityGraphqlAttribute> attributes;

        Builder() {
        }

        public Builder idName(final String idName) {
            this.idName = idName;
            return this;
        }

        public Builder idType(final String idType) {
            this.idType = idType;
            return this;
        }

        public Builder type(final String type) {
            this.type = type;
            return this;
        }

        public Builder embedded(final boolean embedded) {
            this.embedded = embedded;
            return this;
        }

        public Builder attributes(final Set<EntityGraphqlAttribute> attributes) {
            this.attributes = attributes;
            return this;
        }

        public EntityGraphqlType build() {
            return new EntityGraphqlType(this.type, this.attributes, this.embedded, this.idType, this.idName);
        }

        public String toString() {
            return "EntityGraphqlType.EntityGraphqlTypeBuilder(name=" + this.type + ", attributes=" + this.attributes + ", embedded" + this.embedded +")";
        }
    }


}
