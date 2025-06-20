package com.zjj.graphql.component.context;

import com.zjj.autoconfigure.component.graphql.ExcludeTypeProvider;
import com.zjj.graphql.component.spi.EntitySaveHandler;
import com.zjj.graphql.component.utils.RepositoryUtils;
import com.zjj.graphql.component.utils.TypeMatchUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.hibernate.metamodel.AttributeClassification;
import org.hibernate.metamodel.model.domain.internal.*;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.repository.Repository;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月21日 20:30
 */

public class EntityContext {

    private final Map<String, EntityGraphqlType<?>> entityTypes = new HashMap<>();
    @Getter
    private final EntityManager entityManager;
    private final List<ExcludeTypeProvider> excludeTypeProviderList;
    private final List<EntitySaveHandler<?>> saveHandlers;

    public EntityContext(EntityManager entityManager, ObjectProvider<ExcludeTypeProvider> excludeTypeProviders, List<EntitySaveHandler<?>> saveHandlers) {
        this.entityManager = entityManager;
        this.excludeTypeProviderList = excludeTypeProviders.stream().toList();
        this.saveHandlers  = saveHandlers;
        initEntity();
        parseEntity();
    }


    public Collection<EntityGraphqlType<?>> getEntity() {
        return entityTypes.values().stream().filter(entity -> !entity.isEmbedded()).toList();
    }

    public List<? extends EntitySaveHandler<?>> getSaveHandlers(String entityName) {
        if (!entityTypes.containsKey(entityName)) {
            return List.of();
        }
        return entityTypes.get(entityName).getSaveHandlers();
    }

    public QueryEntityProject getQueryEntityProject(String entityName) {
        if (!entityTypes.containsKey(entityName)) {
            return null;
        }
        return entityTypes.get(entityName).getQueryEntityProject();
    }

    public EntityType<?> entity(Repository<?, ?> repository) {
        return entityManager.getMetamodel().entity(RepositoryUtils.getEntityType(repository));
    }

    public Collection<EntityGraphqlType<?>> getEmbeddable() {
        return entityTypes.values().stream().filter(EntityGraphqlType::isEmbedded).toList();
    }

    public Collection<EntityGraphqlType<?>> getManagedType() {
        return entityTypes.values();
    }

    public void forEachEntity(Consumer<EntityGraphqlType> consumer) {
        entityTypes.values().forEach(entity -> {
            if (!entity.isEmbedded()) {
                consumer.accept(entity);
            }
        });
    }

    public void forEachEmbeddable(Consumer<EntityGraphqlType> consumer) {
        entityTypes.values().forEach(entity -> {
            if (entity.isEmbedded()) {
                consumer.accept(entity);
            }
        });
    }

    public void forEachManagedType(Consumer<EntityGraphqlType<?>> consumer) {
        entityTypes.values().forEach(consumer);
    }

    public boolean containsEntity(String entityName) {
        return entityTypes.containsKey(entityName);
    }


    private void parseManageType(ManagedType<?> managedType, EntityGraphqlType.Builder builder) {
        Set<EntityGraphqlAttribute> entityGraphqlAttributes = new HashSet<>();
        for (Attribute<?, ?> attribute : managedType.getAttributes()) {
            boolean flag = attribute.isAssociation();
            boolean flag2 = false;

            EntityGraphqlAttribute.Builder attributeBuilder = EntityGraphqlAttribute.builder();
            attributeBuilder.name(attribute.getName());

            attributeBuilder.association(attribute.isAssociation());

            attributeBuilder.collection(attribute.isCollection());
            Class<?> javaType = attribute.getJavaType();
            Comment annotation = AnnotationUtils.findAnnotation(javaType, Comment.class);
            if (annotation != null) {
                attributeBuilder.description(annotation.value());
            }

            if (attribute.isAssociation()) {
                if (attribute.isCollection()) {
                    attributeBuilder.type(((AbstractAttribute) attribute).getValueGraphType().toString());
//                    attributeBuilder.type(((AbstractPluralAttribute) attribute).getBindableJavaType().getSimpleName());
//                    attributeBuilder.type(((AbstractPluralAttribute) attribute).getBindableJavaType().getSimpleName());
                } else {
                    attributeBuilder.type(((SingularAttributeImpl) attribute).getType().toString());
                }
            }

            if (attribute instanceof SingularAttributeImpl singularAttribute) {
                if (singularAttribute.getAttributeClassification().equals(AttributeClassification.EMBEDDED)) {
                    attributeBuilder.embedded(true);
                    if (attribute.isCollection()) {
//                        attributeBuilder.type(((SetAttributeImpl) attribute).getBindableJavaType().getSimpleName());
                        attributeBuilder.type(((SingularAttributeImpl) attribute).getType().toString());
                    } else {
//                        attributeBuilder.type(attribute.getJavaType().getSimpleName());
                        attributeBuilder.type(((SingularAttributeImpl) attribute).getType().toString());
                    }
                } else {
                    attributeBuilder.embedded(false);
                }
            }

            if (!flag && !flag2) {
                attributeBuilder.type(TypeMatchUtils.matchType(attribute));
            }

            entityGraphqlAttributes.add(attributeBuilder.build());
        }
        builder.attributes(entityGraphqlAttributes);
    }

    protected void initEntity() {
        for (EntityType<?> entity : entityManager.getMetamodel().getEntities()) {
            if (!entity.getJavaType().getName().startsWith("com.zjj")) {
                continue;
            }
            if (this.excludeTypeProviderList.stream().anyMatch(e -> e.exclude().contains(entity.getName()))) {
                continue;
            }
            EntityGraphqlType.Builder builder = EntityGraphqlType.builder();
            builder.idType("ID");
            builder.idName(this.findIdName(entity));
            builder.type(entity.getName());
            builder.embedded(false);
            parseManageType(entity, builder);
            EntityGraphqlType build = builder.build();
            build.initMutation(entity.getJavaType(), saveHandlers);
            build.initQuery(entity.getJavaType());
            entityTypes.put(entity.getName(), build);
        }

        for (EmbeddableType<?> embeddable : entityManager.getMetamodel().getEmbeddables()) {
            EntityGraphqlType.Builder builder = EntityGraphqlType.builder();
            String simpleName = embeddable.getJavaType().getSimpleName();
            builder.type(simpleName);
            builder.embedded(true);
            parseManageType(embeddable, builder);
            entityTypes.put(simpleName, builder.build());
        }
    }

    private String findIdName(EntityType<?> entity) {
        Set<Attribute<?, ?>> attributes = (Set<Attribute<?, ?>>) entity.getAttributes();
        for (Attribute<?, ?> attribute : attributes) {
            if (attribute instanceof SingularAttributeImpl.Identifier<?,?> identifier && identifier.isId()) {
                return attribute.getName();
            }
        }
        return null;
    }

    protected void parseEntity() {
        for (EntityGraphqlType<?> value : entityTypes.values()) {
            for (EntityGraphqlAttribute attribute : value.getAttributes()) {
                if (attribute.getAssociation() || attribute.getEmbedded()) {
                    EntityGraphqlType<?> type = entityTypes.get(attribute.getType());
                    attribute.setAssociationType(type);
                }
            }
        }
    }

}
