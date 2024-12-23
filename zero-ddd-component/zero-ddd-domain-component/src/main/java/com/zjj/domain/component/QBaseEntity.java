package com.zjj.domain.component;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.SimplePath;
import com.querydsl.core.types.dsl.StringPath;

import javax.annotation.processing.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QBaseEntity is a Querydsl query type for BaseEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QBaseEntity extends EntityPathBase<BaseEntity<? extends java.io.Serializable>> {

    private static final long serialVersionUID = -1808444018L;

    public static final QBaseEntity baseEntity = new QBaseEntity("baseEntity");

    public final SimplePath<java.util.Optional<String>> createdBy = createSimple("createdBy", java.util.Optional.class);

    public final SimplePath<java.util.Optional<java.time.LocalDateTime>> createdDate = createSimple("createdDate", java.util.Optional.class);

    public final SimplePath<java.util.Optional<String>> lastModifiedBy = createSimple("lastModifiedBy", java.util.Optional.class);

    public final SimplePath<java.util.Optional<java.time.LocalDateTime>> lastModifiedDate = createSimple("lastModifiedDate", java.util.Optional.class);

    public final StringPath tenantBy = createString("tenantBy");

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QBaseEntity(String variable) {
        super((Class) BaseEntity.class, forVariable(variable));
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QBaseEntity(Path<? extends BaseEntity> path) {
        super((Class) path.getType(), path.getMetadata());
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QBaseEntity(PathMetadata metadata) {
        super((Class) BaseEntity.class, metadata);
    }

}

