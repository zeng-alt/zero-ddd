package com.zjj.graphql.component.query.condition;

/**
 * @author zengJiaJun
 * @crateTime 2024年10月20日 21:32
 * @version 1.0
 */
public enum Option {

    EQ("=="),
    NE("!="),
    NULL("null"),
    NOT_NULL("not null"),
    GT(">"),
    LT("<"),
    GTE(">="),
    LTE("<="),
    IN("in"),
    NOT_IN("not in"),
    LIKE("%%"),
    NOT_LIKE("!%%"),
    LEFT_LIKE("^%"),
    RIGHT_LIKE("%$"),
    BETWEEN("between"),
    NOT_BETWEEN("not between"),
    ;

    String code;

    Option(String s) {
    }
}
