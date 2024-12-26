package com.zjj.domain.component;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.SimplePath;
import com.querydsl.core.types.dsl.StringPath;

import javax.annotation.processing.Generated;

import java.util.Optional;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QBaseAggregate is a Querydsl query type for BaseAggregate
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QBaseAggregate extends EntityPathBase<BaseAggregate<? extends java.io.Serializable>> {

    private static final long serialVersionUID = 1629857844L;

    public static final QBaseAggregate baseAggregate = new QBaseAggregate("baseAggregate");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final SimplePath<java.util.Optional<String>> createdBy = _super.createdBy;

    //inherited
    public final SimplePath<java.util.Optional<java.time.LocalDateTime>> createdDate = _super.createdDate;

    //inherited
    public final SimplePath<java.util.Optional<String>> lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final SimplePath<java.util.Optional<java.time.LocalDateTime>> lastModifiedDate = _super.lastModifiedDate;

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QBaseAggregate(String variable) {
        super((Class) BaseAggregate.class, forVariable(variable));
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QBaseAggregate(Path<? extends BaseAggregate> path) {
        super((Class) path.getType(), path.getMetadata());
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QBaseAggregate(PathMetadata metadata) {
        super((Class) BaseAggregate.class, metadata);
    }

}

