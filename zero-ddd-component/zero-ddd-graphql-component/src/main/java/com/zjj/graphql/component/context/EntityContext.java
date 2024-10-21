package com.zjj.graphql.component.context;

import com.zjj.graphql.component.utils.TypeMatchUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.hibernate.metamodel.AttributeClassification;
import org.hibernate.metamodel.model.domain.internal.SetAttributeImpl;
import org.hibernate.metamodel.model.domain.internal.SingularAttributeImpl;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月21日 20:30
 */

public class EntityContext {

    private final Map<String, EntityGraphqlType> entityTypes = new HashMap<>();
    @Getter
    private final EntityManager entityManager;

    public EntityContext(EntityManager entityManager) {
        this.entityManager = entityManager;
        initEntity();
        parseEntity();
    }

    public void forEachEntity(Consumer<EntityGraphqlType> consumer) {
        entityTypes.values().forEach(consumer);
    }

    public boolean containsEntity(String entityName) {
        return entityTypes.containsKey(entityName);
    }



    protected void initEntity() {
        for (ManagedType<?> managedType : entityManager.getMetamodel().getManagedTypes()) {
            EntityGraphqlType.Builder builder = EntityGraphqlType.builder();

            String simpleName = managedType.getJavaType().getSimpleName();
            builder.type(simpleName);
            Set<EntityGraphqlAttribute> entityGraphqlAttributes = new HashSet<>();
            for (Attribute<?, ?> attribute : managedType.getAttributes()) {
                boolean flag = attribute.isAssociation();
                boolean flag2 = false;

                EntityGraphqlAttribute.Builder attributeBuilder = EntityGraphqlAttribute.builder();
                attributeBuilder.name(attribute.getName());

                attributeBuilder.association(attribute.isAssociation());

                if (attribute.isAssociation()) {
                    if (attribute.isCollection()) {
                        ((SetAttributeImpl) attribute).getBindableJavaType().getSimpleName();
                    } else {
                        attributeBuilder.type(attribute.getJavaType().getSimpleName());
                    }
                }

                attributeBuilder.collection(attribute.isCollection());
                Class<?> javaType = attribute.getJavaType();
                Comment annotation = AnnotationUtils.findAnnotation(javaType, Comment.class);
                if (annotation != null) {
                    attributeBuilder.description(annotation.value());
                }
                if (attribute instanceof SingularAttributeImpl singularAttribute) {
                    if (singularAttribute.getAttributeClassification().equals(AttributeClassification.EMBEDDED)) {
                        attributeBuilder.embedded(true);
                        if (attribute.isCollection()) {
                            attributeBuilder.type(((SetAttributeImpl) attribute).getBindableJavaType().getSimpleName());
                        } else {
                            attributeBuilder.type(attribute.getJavaType().getSimpleName());
                        }
                    } else {
                        attributeBuilder.embedded(false);
                    }
                }

                if (!flag && !flag2) {
                    attributeBuilder.type(TypeMatchUtils.matchType(attribute.getJavaType()));
                }

                entityGraphqlAttributes.add(attributeBuilder.build());
            }
            builder.attributes(entityGraphqlAttributes);
            entityTypes.put(simpleName, builder.build());
        }
    }

    protected void parseEntity() {
        for (EntityGraphqlType value : entityTypes.values()) {
            for (EntityGraphqlAttribute attribute : value.getAttributes()) {
                if (attribute.getAssociation() || attribute.getEmbedded()) {
                    EntityGraphqlType type = entityTypes.get(attribute.getType());
                    attribute.setAssociationType(type);
                }
            }
        }
    }

}
