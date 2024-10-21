package com.zjj.graphql.component.context;

import graphql.language.Comment;
import graphql.language.ListType;
import graphql.language.SourceLocation;
import graphql.language.Type;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年10月21日 21:20
 */
public interface GraphqlType {

    String UNCAPITALIZE_TYPE = "type";
    String CAPITALIZE_TYPE = "Type";
    String UNCAPITALIZE_INPUT = "input";
    String CAPITALIZE_INPUT = "Input";
    String UNCAPITALIZE_CONNECTION = "connection";
    String CAPITALIZE_CONNECTION = "Connection";
    String UNCAPITALIZE_CONDITION = "condition";
    String CAPITALIZE_CONDITION = "Condition";

    default Type wrapCollectionType(Type type, String comment) {
        return ListType
                .newListType(type)
//                .comments(List.of(new Comment(comment, SourceLocation.EMPTY)))
                .build();
    }

    String getName();
    String getType();


    default String getEntityName() {
        return getName();
    }

    default String getInputName() {
        return getName() + CAPITALIZE_INPUT;
    }

    default String getConditionName() {
        return getName() + CAPITALIZE_CONDITION;
    }

    default String getEntityTypeName() {
        return getType();
    }

    default String getInputTypeName() {
        return getType() + CAPITALIZE_INPUT;
    }

    default String getConnectionTypeName() {
        return getType() + CAPITALIZE_CONNECTION;
    }

    default String getConditionTypeName() {
        return getType() + CAPITALIZE_CONDITION;
    }

    Type getEntityType();

    Type getInputType();

    Type getConnectionType();

    Type getConditionType();
}
