package com.zjj.autoconfigure.component.security.rbac;

import com.zjj.bean.componenet.ApplicationContextHelper;
import graphql.language.Definition;
import graphql.language.Field;
import graphql.language.OperationDefinition;
import graphql.language.Selection;
import graphql.parser.Parser;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2024年12月05日 16:51
 */
@Getter
@Setter
public class GraphqlResource extends AbstractResource {
    private String type;
    private List<String> functionNames;
}
